/**
 * eventbus-commons - Core classes for net.segoia.event-bus framework
 * Copyright (C) 2016  Adrian Cristian Ionescu - https://github.com/acionescu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.segoia.event.eventbus.peers.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.segoia.event.eventbus.peers.EventBusNodeConfig;
import net.segoia.event.eventbus.peers.EventNodeContext;
import net.segoia.event.eventbus.peers.PeerContext;
import net.segoia.event.eventbus.peers.PeerEventContext;
import net.segoia.event.eventbus.peers.comm.PeerCommManager;
import net.segoia.event.eventbus.peers.core.IdentitiesManager;
import net.segoia.event.eventbus.peers.core.PrivateIdentityData;
import net.segoia.event.eventbus.peers.core.PrivateIdentityManager;
import net.segoia.event.eventbus.peers.core.PublicIdentityManager;
import net.segoia.event.eventbus.peers.core.PublicIdentityManagerFactory;
import net.segoia.event.eventbus.peers.events.auth.ServiceAccessIdRequestEvent;
import net.segoia.event.eventbus.peers.exceptions.PeerAuthRequestRejectedException;
import net.segoia.event.eventbus.peers.exceptions.PeerCommunicationNegotiationFailedException;
import net.segoia.event.eventbus.peers.exceptions.PeerRequestHandlingException;
import net.segoia.event.eventbus.peers.exceptions.PeerRequestRejectedException;
import net.segoia.event.eventbus.peers.exceptions.PeerSessionException;
import net.segoia.event.eventbus.peers.manager.states.PeerStateContext;
import net.segoia.event.eventbus.peers.security.rules.PeerRuleEngine;
import net.segoia.event.eventbus.peers.util.EventNodeLogger;
import net.segoia.event.eventbus.peers.vo.NodeInfo;
import net.segoia.event.eventbus.peers.vo.RequestRejectReason;
import net.segoia.event.eventbus.peers.vo.auth.PeerAuthRejected;
import net.segoia.event.eventbus.peers.vo.auth.ServiceAccessIdRequest;
import net.segoia.event.eventbus.peers.vo.auth.id.IdentityType;
import net.segoia.event.eventbus.peers.vo.auth.id.NodeIdentity;
import net.segoia.event.eventbus.peers.vo.auth.id.SharedIdentityType;
import net.segoia.event.eventbus.peers.vo.auth.id.SharedNodeIdentity;
import net.segoia.event.eventbus.peers.vo.auth.id.SpkiFullIdentityType;
import net.segoia.event.eventbus.peers.vo.auth.id.SpkiFullNodeIdentity;
import net.segoia.event.eventbus.peers.vo.comm.CommunicationProtocol;
import net.segoia.event.eventbus.peers.vo.comm.CommunicationProtocolConfig;
import net.segoia.event.eventbus.peers.vo.comm.CommunicationProtocolDefinition;
import net.segoia.event.eventbus.peers.vo.comm.EncryptSymmetricOperationDef;
import net.segoia.event.eventbus.peers.vo.comm.EncryptWithPublicCommOperationDef;
import net.segoia.event.eventbus.peers.vo.comm.NodeCommunicationStrategy;
import net.segoia.event.eventbus.peers.vo.comm.SignCommOperationDef;
import net.segoia.event.eventbus.peers.vo.security.ChannelCommunicationPolicy;
import net.segoia.event.eventbus.peers.vo.security.IssueIdentityRequest;
import net.segoia.event.eventbus.peers.vo.security.PeerChannelSecurityPolicy;
import net.segoia.event.eventbus.peers.vo.session.KeyDef;
import net.segoia.event.eventbus.peers.vo.session.SessionInfo;
import net.segoia.event.eventbus.peers.vo.session.SessionKey;
import net.segoia.event.eventbus.peers.vo.session.SessionKeyData;
import net.segoia.event.eventbus.peers.vo.session.SessionKeyPlainData;
import net.segoia.event.eventbus.vo.security.DataAuthLevel;
import net.segoia.event.eventbus.vo.security.EventNodeSecurityException;
import net.segoia.event.eventbus.vo.security.IdentityLinkFullData;
import net.segoia.event.eventbus.vo.security.IdsLinkData;
import net.segoia.event.eventbus.vo.security.NodeIdLinkData;
import net.segoia.event.eventbus.vo.security.SignatureInfo;
import net.segoia.event.eventbus.vo.services.EventNodeServiceDefinition;
import net.segoia.event.eventbus.vo.services.EventNodeServiceRef;
import net.segoia.event.eventbus.vo.services.NodeIdentityProfile;
import net.segoia.event.eventbus.vo.services.ServiceContract;

public abstract class EventNodeSecurityManager {
    private EventNodeSecurityConfig securityConfig;
    private EventBusNodeConfig nodeConfig;
    protected Map<Integer, PrivateIdentityData> privateIdentities = new HashMap<>();

    protected Map<Class<? extends NodeIdentity>, PublicIdentityManagerFactory<?>> publicIdentityBuilders;

    protected Map<CommManagerKey, CommManagerBuilder> commManagerBuilders;

    protected Map<CommManagerKey, CommProtocolContextBuilder<?>> commProtocolContextBuilders;

    protected Map<SharedIdentityType, SessionManagerFactory<?>> sessionManagerBuilders;

    protected Map<String, IdentityRole> identityRoles = new HashMap<>();

    protected NestedOperationDataSerializer defaultDataSerializer;

    protected Map<Class, OperationDataDeserializer<?>> deserializers;

    private CryptoHelper cryptoHelper;

    private PeerRuleEngine peerRuleEngine;
    
    private Map<Class<? extends PrivateIdentityManager>, PrivateIdentityManager> defaultIdentities=new HashMap<>();

    public EventNodeSecurityManager() {
	super();
    }
    
    public EventNodeSecurityManager(EventBusNodeConfig nodeConfig) {
	this.nodeConfig = nodeConfig;
	this.securityConfig = nodeConfig.getSecurityConfig();
	init();
    }

    public EventNodeSecurityManager(EventNodeSecurityConfig securityConfig) {
	super();

	this.securityConfig = securityConfig;
	init();
    }
    
    protected void init() {
	loadIdentities();
	initCommProtocolContextBuilders();
	initPublicIdentityBuilders();
	initSerializers();
	initDeserializers();
	initCommBuilders();
	initSessionManagerBuilders();

	initIdentityRoles();
    }

    protected abstract void initDeserializers();

    protected abstract void initSerializers();

    protected abstract void initSessionManagerBuilders();

    protected void initCommProtocolContextBuilders() {
	commProtocolContextBuilders = new HashMap<>();

	commProtocolContextBuilders.put(new CommManagerKey("SPKI", "SPKI", null, PeerCommManager.SESSION_COMM),
		new CommProtocolContextBuilder<SpkiCommProtocolContext>() {

		    @Override
		    public SpkiCommProtocolContext build(PeerCommContext context) {
			PeerContext peerContext = context.getPeerContext();

			SpkiCommProtocolContext commProtocolContext = new SpkiCommProtocolContext(
				context.getTxStrategy().getSessionTxStrategy(),
				context.getRxStrategy().getSessionTxStrategy(),
				(SpkiPrivateIdentityManager) peerContext.getOurIdentityManager(),
				(SpkiPublicIdentityManager) peerContext.getPeerIdentityManager());

			return commProtocolContext;
		    }
		});

	commProtocolContextBuilders.put(new CommManagerKey("SPKI", "SPKI", "AES", PeerCommManager.DIRECT_COMM),
		new CommProtocolContextBuilder<CombinedCommProtocolContext>() {

		    @Override
		    public CombinedCommProtocolContext build(PeerCommContext context) {
			PeerContext peerContext = context.getPeerContext();

			CombinedCommProtocolContext commProtocolContext = new CombinedCommProtocolContext(
				context.getTxStrategy().getDirectTxStrategy(),
				context.getRxStrategy().getDirectTxStrategy(), peerContext.getSessionManager(),
				(SpkiPrivateIdentityManager) peerContext.getOurIdentityManager(),
				(SpkiPublicIdentityManager) peerContext.getPeerIdentityManager());

			return commProtocolContext;
		    }
		});
    }

    protected abstract void initPublicIdentityBuilders();

    protected void initCommBuilders() {
	if (commManagerBuilders == null) {
	    commManagerBuilders = new HashMap<>();
	}

	CommManagerConfig config = new CommManagerConfig();

	/* add tx operations */
	config.addTxOperation(SignCommOperationDef.TYPE, new SignCommOperation());
	config.addTxOperation(EncryptWithPublicCommOperationDef.TYPE, new EncryptWithPublicCommOperation());

	/* add rx operations */
	config.addRxOperation(SignCommOperationDef.TYPE, new VerifySignatureCommOperation());
	config.addRxOperation(EncryptWithPublicCommOperationDef.TYPE, new DecryptWithPrivateCommOperation());

	/* add symmetric operations */
	config.addTxOperation(EncryptSymmetricOperationDef.TYPE, new EncryptSymmetricCommOperation());
	config.addRxOperation(EncryptSymmetricOperationDef.TYPE, new DecryptSymmetricCommOperation());

	/* add tx operation context builders */
	config.addTxOpContextBuilder(SignCommOperationDef.TYPE,
		new SpkiCommOperationContextBuilder<SignCommOperationDef>() {

		    @Override
		    public OperationContext buildContext(SignCommOperationDef def, SpkiCommProtocolContext context) {
			SignCommOperationContext oc = new SignCommOperationContext(def, context.getOurIdentity(),
				context.getPeerIdentity());
			oc.setDefaultSerializer(defaultDataSerializer);
			return oc;
		    }

		});

	config.addTxOpContextBuilder(EncryptWithPublicCommOperationDef.TYPE,
		new SpkiCommOperationContextBuilder<EncryptWithPublicCommOperationDef>() {

		    @Override
		    public OperationContext buildContext(EncryptWithPublicCommOperationDef def,
			    SpkiCommProtocolContext context) {
			EncryptWithPublicOperationContext oc = new EncryptWithPublicOperationContext(def,
				context.getOurIdentity(), context.getPeerIdentity());
			oc.setDefaultSerializer(defaultDataSerializer);
			return oc;
		    }
		});

	/* add rx operation context builder */

	config.addRxOpContextBuilder(SignCommOperationDef.TYPE,
		new SpkiCommOperationContextBuilder<SignCommOperationDef>() {

		    @Override
		    public OperationContext buildContext(SignCommOperationDef def, SpkiCommProtocolContext context) {

			VerifySignatureOperationContext rc = new VerifySignatureOperationContext(def,
				context.getOurIdentity(), context.getPeerIdentity());

			rc.setInputDeserializers(deserializers);
			rc.setDefaultSerializer(defaultDataSerializer);
			return rc;
		    }
		});

	config.addRxOpContextBuilder(EncryptWithPublicCommOperationDef.TYPE,
		new SpkiCommOperationContextBuilder<EncryptWithPublicCommOperationDef>() {

		    @Override
		    public OperationContext buildContext(EncryptWithPublicCommOperationDef def,
			    SpkiCommProtocolContext context) {
			DecryptWithPrivateOperationContext oc = new DecryptWithPrivateOperationContext(def,
				context.getOurIdentity(), context.getPeerIdentity());
			oc.setDefaultSerializer(defaultDataSerializer);
			return oc;
		    }
		});

	/* add symmetric operation context builders */

	config.addTxOpContextBuilder(EncryptSymmetricOperationDef.TYPE,
		new CombinedCommOperationContextBuilder<EncryptSymmetricOperationDef>() {

		    @Override
		    public OperationContext<EncryptSymmetricOperationDef> buildContext(EncryptSymmetricOperationDef def,
			    CombinedCommProtocolContext context) {
			EncryptSymmetricCommOperationContext oc = new EncryptSymmetricCommOperationContext(def,
				context.getSharedIdentityManager());
			oc.setDefaultSerializer(defaultDataSerializer);
			return oc;
		    }
		});

	config.addRxOpContextBuilder(EncryptSymmetricOperationDef.TYPE,
		new CombinedCommOperationContextBuilder<EncryptSymmetricOperationDef>() {

		    @Override
		    public OperationContext<EncryptSymmetricOperationDef> buildContext(EncryptSymmetricOperationDef def,
			    CombinedCommProtocolContext context) {
			DecryptSymmetricCommOperationContext oc = new DecryptSymmetricCommOperationContext(def,
				context.getSharedIdentityManager());
			oc.setDefaultSerializer(defaultDataSerializer);
			return oc;
		    }
		});

	commManagerBuilders.put(new CommManagerKey("SPKI", "SPKI", null, PeerCommManager.SESSION_COMM),
		new SpkiSpkiCommManagerBuilder(config));

	commManagerBuilders.put(new CommManagerKey(null, null, null, PeerCommManager.SESSION_COMM),
		new CommManagerBuilder() {

		    @Override
		    public CommManager build(CommProtocolContext context) {
			return new PlainCommManager();
		    }
		});

	commManagerBuilders.put(new CommManagerKey("SPKI", "SPKI", "AES", PeerCommManager.DIRECT_COMM),
		new CombinedCommManagerBuilder(config));
    }

    protected void loadIdentities() {
	List<PrivateIdentityDataLoader<?>> identityLoaders = securityConfig.getIdentityLoaders();
	if (identityLoaders == null) {
	    return;
	}

	List nodeIdentities = securityConfig.getNodeAuth().getIdentities();

	if (nodeIdentities == null) {
	    nodeIdentities = new ArrayList<>();
	    securityConfig.getNodeAuth().setIdentities(nodeIdentities);
	}

	for (PrivateIdentityDataLoader<?> l : identityLoaders) {
	    l.load();
	    PrivateIdentityData<?> data = (PrivateIdentityData) l.getData();

	    if(data == null) {
		continue;
	    }
	    
	    /* save private identity under the index of the position in the node identities */
	    int index = nodeIdentities.size();
	    privateIdentities.put(index, data);
	    data.setIndex(index);

	    NodeIdentity publicNodeIdentity = data.getPublicNodeIdentity();

	    nodeIdentities.add(publicNodeIdentity);
	}
	
	if(nodeIdentities.size() == 0) {
	    handleNoIdentity();
	}
    }
    
    /**
     * This is called if no identity could be loaded for this node
     */
    protected void handleNoIdentity() {
	/* by default, we do nothing */
    }

    protected abstract void initIdentityRoles();

    /**
     * Builds a {@link AbstractCommManager} for a peer
     * 
     * @param context
     * @return
     */
    public CommManager getSessionCommManager(PeerCommContext context) {
	/*
	 * Create a public identity manager for peer and save it on peerContext
	 * 
	 */

	PublicIdentityManager peerIdentity = getPeerIdentity(context);
	PrivateIdentityManager ourIdentity = getOurIdentity(context);

	String ourIdentityType = null;
	if (ourIdentity != null) {
	    ourIdentityType = ourIdentity.getType();
	}

	String peerIdentityType = null;
	if (peerIdentity != null) {
	    peerIdentityType = peerIdentity.getType();
	}

	CommManagerKey commManagerKey = new CommManagerKey(ourIdentityType, peerIdentityType, null,
		PeerCommManager.SESSION_COMM);

	CommProtocolContext commProtocolContext = commProtocolContextBuilders.get(commManagerKey).build(context);

	CommManagerBuilder commManagerBuilder = commManagerBuilders.get(commManagerKey);

	CommManager commManager = commManagerBuilder.build(commProtocolContext);

	return commManager;

    }

    public CommManager getDirectCommManager(PeerCommContext context) {
	PeerContext peerContext = context.getPeerContext();

	// PrivateIdentityManager ourIdentityManager = peerContext.getOurIdentityManager();
	// PublicIdentityManager peerIdentityManager = peerContext.getPeerIdentityManager();

	PrivateIdentityManager ourIdentityManager = getOurIdentity(context);
	PublicIdentityManager peerIdentityManager = getPeerIdentity(context);

	CommManagerKey commManagerKey = new CommManagerKey(ourIdentityManager.getType(), peerIdentityManager.getType(),
		peerContext.getSessionManager().getIdentityType(), PeerCommManager.DIRECT_COMM);

	CommManagerBuilder commManagerBuilder = commManagerBuilders.get(commManagerKey);
	SessionManager sessionManager = peerContext.getSessionManager();

	CommProtocolContext commProtocolContext = commProtocolContextBuilders.get(commManagerKey).build(context);

	CommManager commManager = commManagerBuilder.build(commProtocolContext);

	return commManager;
    }

    private PrivateIdentityManager getOurIdentity(PeerCommContext pcc) {

	PeerContext peerContext = pcc.getPeerContext();

	PrivateIdentityManager privateIdentityData = peerContext.getOurIdentityManager();
	if (privateIdentityData != null) {
	    return privateIdentityData;
	}

	List<PrivateIdentityData<?>> ourAvailableIdentities = peerContext.getOurAvailableIdentities();

	if (ourAvailableIdentities != null) {
	    privateIdentityData = ourAvailableIdentities.get(pcc.getOurIdentityIndex());
	} else {
	    privateIdentityData = privateIdentities.get(pcc.getOurIdentityIndex());
	}
	peerContext.setOurIdentityManager(privateIdentityData);
	return privateIdentityData;
    }

    private PublicIdentityManager getPeerIdentity(PeerCommContext pcc) {
	int peerIdentityIndex = pcc.getPeerIdentityIndex();

	PeerContext peerContext = pcc.getPeerContext();

	PublicIdentityManager peerIdentityManager = peerContext.getPeerIdentityManager();
	if (peerIdentityManager != null) {
	    return peerIdentityManager;
	}
	List<? extends NodeIdentity<? extends IdentityType>> peerIdentities = peerContext.getPeerInfo().getNodeAuth()
		.getIdentities();

	if (peerIdentityIndex < 0 || peerIdentityIndex >= peerIdentities.size()) {
	    return null;
	}

	NodeIdentity<? extends IdentityType> nodeIdentity = peerIdentities.get(peerIdentityIndex);

	PublicIdentityManagerFactory ib = publicIdentityBuilders.get(nodeIdentity.getClass());

	if (ib == null) {
	    return null;
	}

	peerIdentityManager = ib.build(nodeIdentity);

	peerContext.setPeerIdentityManager(peerIdentityManager);
	return peerIdentityManager;
    }

    /**
     * Override this to filter through the peer provided identities
     * 
     * @param peerContext
     * @return
     */
    public List<? extends NodeIdentity<?>> getValidPeerIdentities(PeerContext peerContext) {
	return peerContext.getPeerInfo().getNodeAuth().getIdentities();
    }

    public CommunicationProtocol establishPeerCommunicationProtocol(PeerContext peerContext)
	    throws PeerCommunicationNegotiationFailedException, PeerAuthRequestRejectedException {

	List<? extends NodeIdentity<? extends IdentityType>> validPeerIdentities = getValidPeerIdentities(peerContext);

	if (validPeerIdentities.size() == 0) {
	    /* if no valid peer identity found, throw an exception */
	    throw new PeerAuthRequestRejectedException(
		    new PeerAuthRejected(new RequestRejectReason("No valid peer identity provided")));

	}

	/* Get the most preferred matching protocol */

	String channel = peerContext.getCommunicationChannel();

	PeerChannelSecurityPolicy localChannelPolicy = securityConfig.getSecurityPolicy().getChannelPolicy(channel);
	PeerChannelSecurityPolicy peerChannelPolicy = peerContext.getPeerInfo().getSecurityPolicy()
		.getChannelPolicy(channel);

	ChannelCommunicationPolicy localCommPolicy = localChannelPolicy.getCommunicationPolicy();
	ChannelCommunicationPolicy peerCommPolicy = peerChannelPolicy.getCommunicationPolicy();

	/* if now communication policy is set, use plain protocol */
	if (localCommPolicy == null && peerCommPolicy == null) {
	    return CommunicationProtocol.buildPlainProtocol();
	}

	List<? extends NodeIdentity<? extends IdentityType>> localIdentities = securityConfig.getNodeAuth()
		.getIdentities();

	NodeInfo ourNodeInfo = peerContext.getOurNodeInfo();
	/* if we a different node info is specified, then use that */
	if (ourNodeInfo != null) {
	    localIdentities = ourNodeInfo.getNodeAuth().getIdentities();
	}

	/* see if we can match a local tx strategy with a peer rx strategy */

	List<StrategyIdentitiesPair> localAsTxStrategyIdentitiesPairs = getMatchingCommStrategy(
		localCommPolicy.getSupportedTxStrategies(), peerCommPolicy.getSupportedRxStrategies(), localIdentities,
		validPeerIdentities);

	if (localAsTxStrategyIdentitiesPairs.size() == 0) {
	    throw new PeerCommunicationNegotiationFailedException("Can't find a supported local tx strategy");
	}

	/* see if we can match a local rx strategy with a peer tx strategy */
	List<StrategyIdentitiesPair> peerAsTxStrategyIdentitiesPairs = getMatchingCommStrategy(
		peerCommPolicy.getSupportedTxStrategies(), localCommPolicy.getSupportedRxStrategies(),
		validPeerIdentities, localIdentities);

	if (peerAsTxStrategyIdentitiesPairs.size() == 0) {
	    throw new PeerCommunicationNegotiationFailedException("Can't find a supported local rx strategy");
	}

	/* we now have to find the pairs that use the same identities */

	for (StrategyIdentitiesPair localAsTxPair : localAsTxStrategyIdentitiesPairs) {
	    for (StrategyIdentitiesPair peerAsTxPair : peerAsTxStrategyIdentitiesPairs) {
		if (localAsTxPair.txRxMatches(peerAsTxPair)) {
		    /* we have found supported tx and rx strategies using one identity for each node */
		    if (peerContext.isInServerMode()) {
			return buildCommunicationProtocol(peerAsTxPair, localAsTxPair);
		    } else {
			return buildCommunicationProtocol(localAsTxPair, peerAsTxPair);
		    }
		}
	    }
	}

	throw new PeerCommunicationNegotiationFailedException(
		"Failed to find a protocol for the provided identities and strategies");
    }

    private CommunicationProtocol buildCommunicationProtocol(StrategyIdentitiesPair serverPair,
	    StrategyIdentitiesPair clientPair) {
	CommunicationProtocolDefinition communicationProtocolDefinition = new CommunicationProtocolDefinition(
		serverPair.strategy, serverPair.strategy);
	CommunicationProtocolConfig communicationProtocolConfig = new CommunicationProtocolConfig(
		serverPair.txNodeIdentity, serverPair.rxNodeIdentity);
	return new CommunicationProtocol(communicationProtocolDefinition, communicationProtocolConfig);
    }

    /**
     * Returns all strategy identities pairs found
     * 
     * @param txStrategies
     * @param rxStrategies
     * @param txNodeIdentities
     * @param rxNodeIdentities
     * @return
     */
    private List<StrategyIdentitiesPair> getMatchingCommStrategy(List<NodeCommunicationStrategy> txStrategies,
	    List<NodeCommunicationStrategy> rxStrategies, List<? extends NodeIdentity<?>> txNodeIdentities,
	    List<? extends NodeIdentity<?>> rxNodeIdentities) {

	List<StrategyIdentitiesPair> strategyIdentitiesPairs = new ArrayList<>();

	for (NodeCommunicationStrategy txs : txStrategies) {
	    for (NodeCommunicationStrategy rxs : rxStrategies) {
		if (txs != null && txs.equals(rxs)) {
		    /*
		     * cool, we have found a matching strategy, now let's see if we find matching identities with this
		     * as well
		     */
		    int txNodeIdentity = getNodeIdentityForType(txs.getTxNodeIdentityType(), txNodeIdentities);
		    if (txNodeIdentity < 0) {
			/* not found */
			continue;
		    }

		    int rxNodeIdentity = getNodeIdentityForType(txs.getRxNodeIdentityType(), rxNodeIdentities);

		    if (rxNodeIdentity < 0) {
			/* not found */
			continue;
		    }

		    /* we have found a strategy and identities pair, add it to the list */
		    strategyIdentitiesPairs.add(new StrategyIdentitiesPair(txs, txNodeIdentity, rxNodeIdentity));
		}
	    }
	}
	return strategyIdentitiesPairs;
    }

    private int getNodeIdentityForType(IdentityType type, List<? extends NodeIdentity<?>> identitiesList) {
	int index = 0;
	for (NodeIdentity<?> identity : identitiesList) {
	    if (identity.getType().equals(type)) {
		return index;
	    }
	    index++;
	}
	return -1;
    }

    public SessionKey generateNewSessionKey(PeerContext peerContext) throws PeerSessionException {
	// String channel = peerContext.getCommunicationChannel();
	// PeerChannelSecurityPolicy localChannelPolicy = securityConfig.getSecurityPolicy().getChannelPolicy(channel);
	// ChannelSessionPolicy sessionPolicy = localChannelPolicy.getCommunicationPolicy().getSessionPolicy();

	SharedIdentityType settledSharedIdentityType = (SharedIdentityType) peerContext.getPeerCommContext()
		.getTxStrategy().getSharedIdentityType();

	/*
	 * Get session key definition
	 */
	// KeyDef sessionKeyDef = sessionPolicy.getSessionKeyDef();
	KeyDef sessionKeyDef = settledSharedIdentityType.getKeyDef();

	int maxSupportedKeySize = sessionKeyDef.getKeySize();

	PublicIdentityManager peerIdentityManager = peerContext.getPeerIdentityManager();
	if (peerIdentityManager != null) {
	    maxSupportedKeySize = peerIdentityManager.getMaxSupportedEncryptedDataBlockSize();
	}

	try {
	    // SecretKey secretKey = CryptoUtil.generateSecretkey(sessionKeyDef.getAlgorithm(), maxSupportedKeySize);
	    // byte[] secretKeyBytes = secretKey.getEncoded();

	    byte[] secretKeyBytes = getCryptoHelper().generateSecretKey(sessionKeyDef.getAlgorithm(),
		    maxSupportedKeySize);

	    KeyDef newSessionKeyDef = new KeyDef(sessionKeyDef.getAlgorithm(), maxSupportedKeySize);

	    SessionKey sessionKey = new SessionKey(peerContext.getNodeContext().generateSessionId(), secretKeyBytes,
		    newSessionKeyDef);

	    /* generate an initialization vector */
	    // SecureRandom sr = new SecureRandom();
	    // byte[] iv = new byte[maxSupportedKeySize / 8];
	    // sr.nextBytes(iv);

	    byte[] iv = getCryptoHelper().generateIv(maxSupportedKeySize / 8);
	    sessionKey.setIv(iv);

	    /* build a session manager and set it on context */
	    SharedIdentityType sharedIdentityType = new SharedIdentityType(newSessionKeyDef);
	    SessionManager sessionManager = sessionManagerBuilders.get(sharedIdentityType)
		    .build(new SharedNodeIdentity(sharedIdentityType, secretKeyBytes, iv));

	    peerContext.setSessionManager(sessionManager);
	    peerContext.setSessionKey(sessionKey);
	    return sessionKey;

	} catch (Exception e) {
	    throw new PeerSessionException("Failed to generate session key with algorithm "
		    + sessionKeyDef.getAlgorithm() + " and size " + maxSupportedKeySize, e);
	}

    }

    public void buildSessionFromSessionInfo(PeerContext peerContext, SessionInfo sessionInfo)
	    throws CommOperationException {
	SessionKeyData sessionKeyData = sessionInfo.getSessionData();
	KeyDef keyDef = sessionKeyData.getKeyDef();
	/* decode base 64 string */
	byte[] sessionTokenBytes = cryptoHelper.base64Decode(sessionKeyData.getSessionToken());
	byte[] sessionSignatureBytes = cryptoHelper.base64Decode(sessionKeyData.getSessionTokenSignature());
	byte[] ivBytes = cryptoHelper.base64Decode(sessionKeyData.getKeyIv());

	/* build a signatureObject */
	SignCommOperationOutput signCommOperationOutput = new SignCommOperationOutput(sessionTokenBytes,
		sessionSignatureBytes);

	/* now feed this to the session comm manager */

	PeerCommManager peerCommManager = peerContext.getPeerCommManager();

	CommDataContext processedSessionData = peerCommManager
		.processIncomingSessionData(new CommDataContext(signCommOperationOutput));

	/* build a session manager and set it on context */
	SharedIdentityType sharedIdentityType = new SharedIdentityType(keyDef);
	final byte[] keyBytes = processedSessionData.getData();
	SessionManager sessionManager = sessionManagerBuilders.get(sharedIdentityType)
		.build(new SharedNodeIdentity(sharedIdentityType, keyBytes, ivBytes));

	peerContext.setSessionManager(sessionManager);

	SessionKey sessionKey = new SessionKey(sessionInfo.getSessionId(), keyBytes, keyDef, ivBytes);
	peerContext.setSessionKey(sessionKey);
    }

    public void buildSessionFromPlainData(PeerContext peerContext, SessionKeyPlainData sessionData)
	    throws CommOperationException {
	byte[] keyBytes = cryptoHelper.base64Decode(sessionData.getKeyData());
	byte[] ivBytes = cryptoHelper.base64Decode(sessionData.getIvData());
	KeyDef keyDef = sessionData.getKeyDef();

	/* build a session manager and set it on context */
	SharedIdentityType sharedIdentityType = new SharedIdentityType(keyDef);
	SessionManager sessionManager = sessionManagerBuilders.get(sharedIdentityType)
		.build(new SharedNodeIdentity(sharedIdentityType, keyBytes, ivBytes));

	peerContext.setSessionManager(sessionManager);

	SessionKey sessionKey = new SessionKey(sessionData.getSessionId(), keyBytes, keyDef, ivBytes);
	peerContext.setSessionKey(sessionKey);
    }

    private class StrategyIdentitiesPair {
	NodeCommunicationStrategy strategy;
	int txNodeIdentity;
	int rxNodeIdentity;

	public StrategyIdentitiesPair(NodeCommunicationStrategy strategy, int txNodeIdentity, int rxNodeIdentity) {
	    super();
	    this.strategy = strategy;
	    this.txNodeIdentity = txNodeIdentity;
	    this.rxNodeIdentity = rxNodeIdentity;
	}

	/**
	 * Checks if another pair matches as a communication partner
	 * 
	 * @param peerPair
	 * @return
	 */
	public boolean txRxMatches(StrategyIdentitiesPair peerPair) {
	    return (txNodeIdentity == peerPair.rxNodeIdentity) && (rxNodeIdentity == peerPair.txNodeIdentity);
	}
    }

    public NodeIdentityProfile getIdentityProfile(String identityKey) {
	return securityConfig.getIdentitiesManager().getIdentityProfile(identityKey);
    }

    public NodeIdentityProfile getCurrentPeerIdentityProfile(PeerContext peerContext) {
	PublicIdentityManager peerIdentityManager = peerContext.getPeerIdentityManager();
	if (peerIdentityManager != null) {
	    return getIdentityProfile(peerIdentityManager.getIdentityKey());
	}
	return null;
    }

    public boolean isEventAccepted(PeerStateContext context) {
	PeerContext peerContext = context.getPeerContext();
	NodeIdentityProfile currentPeerIdentityProfile = getCurrentPeerIdentityProfile(peerContext);
	for (String roleId : currentPeerIdentityProfile.getRoles()) {
	    IdentityRole identityRole = identityRoles.get(roleId);
	    if (identityRole != null) {
		boolean accepted = identityRole.isEventAccepted(context);
		if (accepted) {
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * Returns an existing identity for the specified services access or creates one if instructed so
     * 
     * @param c
     * @param create
     * @return
     * @throws PeerRequestRejectedException
     * @throws PeerRequestHandlingException
     */
    public NodeIdentityProfile getValidIdentityForServiceAccess(PeerEventContext<ServiceAccessIdRequestEvent> c,
	    boolean create) throws PeerRequestRejectedException, PeerRequestHandlingException {
	ServiceAccessIdRequestEvent event = c.getEvent();
	ServiceAccessIdRequest request = event.getData();

	PeerContext peerContext = c.getPeerManager().getPeerContext();
	/* get the current identity */
	PublicIdentityManager peerIdentityManager = peerContext.getPeerIdentityManager();
	if (peerIdentityManager == null) {
	    /* we need to see if we can issue a new identity without any authentication. For now just throw exception */
	    throw new PeerRequestRejectedException("Authentication is required to issue new identity", peerContext);
	}

	String currentIdentityKey = peerIdentityManager.getIdentityKey();
	/* get profile for current identity */
	IdentitiesManager identitiesManager = securityConfig.getIdentitiesManager();
	NodeIdentityProfile currentIdentityProfile = identitiesManager.getIdentityProfile(currentIdentityKey);

	if (currentIdentityProfile == null) {
	    /* a profile should already be present for this identity. throw Error */
	    throw new PeerRequestHandlingException("No profile found for identity " + currentIdentityKey, peerContext);
	}

	List<EventNodeServiceRef> targetServices = request.getTargetServices();

	if (targetServices == null || targetServices.size() == 0) {
	    throw new PeerRequestRejectedException("A service access request must specify at least a service",
		    peerContext);
	}

	/* check if child identities allow access to requested services */

	List<String> childIdentities = currentIdentityProfile.getChildIdentityKeysList();

	if (childIdentities != null) {
	    /* check if the child identities support the requested services */
	    for (String childIdKey : childIdentities) {
		NodeIdentityProfile childIdProfile = identitiesManager.getIdentityProfile(childIdKey);
		if (childIdProfile == null) {
		    throw new PeerRequestHandlingException(
			    "No profile found for child id " + childIdKey + " parent id key " + currentIdentityKey,
			    peerContext);
		}
		if (childIdProfile.areServicesAccessible(targetServices)) {
		    return childIdProfile;
		}
	    }
	}

	if (create) {
	    /* issue a new identity */
	    NodeIdentityProfile newAccessIdentityProfile = issueServiceAccessIdentity(c);
	    currentIdentityProfile.addChildIdentityKey(newAccessIdentityProfile.getIdentityKey());
	    newAccessIdentityProfile.setParentIdentityKey(currentIdentityKey);

	    /* save the profile for later use */
	    identitiesManager.storeIdentityProfile(newAccessIdentityProfile);

	    /* update the current identity profile */
	    identitiesManager.storeIdentityProfile(currentIdentityProfile);

	    return newAccessIdentityProfile;
	}

	return null;
    }

    public NodeIdentityProfile issueServiceAccessIdentity(PeerEventContext<ServiceAccessIdRequestEvent> c) {
	ServiceAccessIdRequestEvent event = c.getEvent();
	ServiceAccessIdRequest request = event.getData();

	// TODO: verify that this peer is allowed to request this

	// TODO: verify that we actually provide the requested services

	EventNodeContext nodeContext = c.getPeerManager().getNodeContext();

	List<EventNodeServiceRef> targetServices = request.getTargetServices();
	if (targetServices == null) {
	    // throw error
	}

	/* build a map with required service contracts */
	Map<String, ServiceContract> serviceContracts = new HashMap<>();

	for (EventNodeServiceRef sref : targetServices) {
	    EventNodeServiceDefinition serviceDef = nodeContext.getService(sref);
	    if (serviceDef == null) {
		// throw error
	    }
	    // TODO: do extra checks if the peer is allowed to use this service
	    ServiceContract serviceContract = new ServiceContract();
	    serviceContract.setServiceRef(sref);
	    serviceContracts.put(sref.toString(), serviceContract);
	}

	IdentitiesManager identitiesManager = securityConfig.getIdentitiesManager();
	// TODO: use configuration to set the default identity type
	NodeIdentity<?> issuedIdentity = identitiesManager
		.issueIdentity(new IssueIdentityRequest(new SpkiFullIdentityType(new KeyDef("RSA", 1024))));

	/* build a profile for this identity */

	NodeIdentityProfile issuedIdentityProfile = new NodeIdentityProfile(issuedIdentity);
	issuedIdentityProfile.setIdentityKey(getIdentityKeyForNodeIdentity(issuedIdentity));

	/* set parent identity key */
	PeerContext peerContext = c.getPeerManager().getPeerContext();
	String parentIdentityKey = peerContext.getPeerIdentityManager().getIdentityKey();
	issuedIdentityProfile.setParentIdentityKey(parentIdentityKey);
	issuedIdentityProfile.setRootIdentityKey(parentIdentityKey);

	/* add service contracts to the profile */
	issuedIdentityProfile.setServiceContracts(serviceContracts);
	/* set role */
	issuedIdentityProfile.addRole(IdentityRole.SERVICE_ACCESS);

	return issuedIdentityProfile;
    }

    public String getIdentityKeyForNodeIdentity(NodeIdentity<?> nodeIdentity) {
	if (nodeIdentity instanceof SpkiFullNodeIdentity) {
	    SpkiFullNodeIdentity sfni = (SpkiFullNodeIdentity) nodeIdentity;
	    return cryptoHelper.sha256(sfni.getPubKey());
	}
	throw new RuntimeException("Can't compute identity key for identity " + nodeIdentity.getClass());
    }

    public void onPeerNodeAuth(PeerContext peerContext) {
	PublicIdentityManager peerIdentityManager = getPeerIdentity(peerContext.getPeerCommContext());
	String identityKey = peerIdentityManager.getIdentityKey();
	IdentitiesManager identitiesManager = securityConfig.getIdentitiesManager();
	NodeIdentityProfile identityProfile = identitiesManager.getIdentityProfile(identityKey);

	if (identityProfile == null) {
	    /* this identity is new to us, create a profile */
	    identityProfile = new NodeIdentityProfile(peerIdentityManager.getIdentityKey(),
		    peerIdentityManager.getIdentity());
	    /* if this identity is unknown, then it must be a personal peer id */
	    identityProfile.addRole(IdentityRole.PEER_AUTH);
	}

	identityProfile.setLastAuthTs(System.currentTimeMillis());
	/* store peer identity profile */
	identitiesManager.storeIdentityProfile(identityProfile);
    }

    public CryptoHelper getCryptoHelper() {
	return cryptoHelper;
    }

    public void setCryptoHelper(CryptoHelper cryptoHelper) {
	this.cryptoHelper = cryptoHelper;
    }

    public PeerRuleEngine getPeerRuleEngine() {
	return peerRuleEngine;
    }

    public void setPeerRuleEngine(PeerRuleEngine peerRuleEngine) {
	this.peerRuleEngine = peerRuleEngine;
    }

    /**
     * This will return the auth level relative to the current peer identity
     * 
     * @param pc
     * @param sigInfo
     * @param data
     * @return
     */
    public DataAuthLevel getDataAuthLevel(PeerContext pc, SignatureInfo sigInfo, byte[] data) {
	if (sigInfo == null || data == null) {
	    return null;
	}

	/* verify the signature */

	NodeIdentityProfile currentPeerIdentityProfile = pc.getCurrentPeerIdentityProfile();

	String rootIdentityKey = currentPeerIdentityProfile.getRootIdentityKey();

	if (rootIdentityKey.equals(sigInfo.getIdKey())) {
	    /* verify that this data is signed with root key */
	    NodeIdentityProfile rootIdProfile = getIdentityProfile(rootIdentityKey);

	    NodeIdentity<? extends IdentityType> rootIdentity = rootIdProfile.getIdentity();
	    PublicIdentityManagerFactory publicIdentityManagerFactory = publicIdentityBuilders
		    .get(rootIdentity.getClass());

	    PublicIdentityManager rootPublicIdManager = publicIdentityManagerFactory.build(rootIdentity);

	    if (rootPublicIdManager instanceof SpkiPublicIdentityManager) {
		SpkiPublicIdentityManager spkiRootIdManager = (SpkiPublicIdentityManager) rootPublicIdManager;

		VerifySignatureOperationWorker sgnVerificationWorker = null;
		try {
		    sgnVerificationWorker = spkiRootIdManager.buildVerifySignatureWorker(sigInfo.getSignatureDef());
		} catch (Exception e) {
		    throw new RuntimeException("Failed to build signature verification worker for " + sigInfo, e);
		}

		try {
		    String encodedSignature = sigInfo.getSignature();
		    boolean valid = sgnVerificationWorker.verify(data, cryptoHelper.base64Decode(encodedSignature));

		    if (!valid) {
			throw new RuntimeException(
				"Invalid signature from " + pc.getPeerId() + " with id key " + rootIdentityKey);
		    }

		    return new DataAuthLevel(0, valid, rootIdProfile);

		} catch (Exception e) {
		    throw new RuntimeException("Failed to verify signature " + sigInfo, e);
		}
	    }

	}

	return null;
    }

    public void storeIdsLinkData(IdsLinkData data) {
	IdentitiesManager identitiesManager = securityConfig.getIdentitiesManager();

	identitiesManager.storeIdsLinkData(data);
    }

    public IdsLinkData getIdsLinkData(String idsLinkKey) {
	IdentitiesManager identitiesManager = securityConfig.getIdentitiesManager();

	return identitiesManager.getIdsLinkData(idsLinkKey);
    }

    public void removeIdsLinkData(String idsLinkKey) {
	IdentitiesManager identitiesManager = securityConfig.getIdentitiesManager();

	identitiesManager.removeIdsLinkData(idsLinkKey);
    }

    public Map<String, NodeIdLinkData> getAllLinksForIdKey(String idKey) {
	IdentitiesManager identitiesManager = securityConfig.getIdentitiesManager();

	return identitiesManager.getAllLinksForIdKey(idKey);
    }

    public void storeIdentityLinkFullData(IdentityLinkFullData data) {
	IdentitiesManager identitiesManager = securityConfig.getIdentitiesManager();

	identitiesManager.storeIdentityLinkFullData(data);
    }

    public IdentityLinkFullData getIdentityLinkFullData(String idsLinkKey) {
	IdentitiesManager identitiesManager = securityConfig.getIdentitiesManager();
	return identitiesManager.getIdentityLinkFullData(idsLinkKey);
    }

    public boolean removeIdentityLinkFullData(String identityKey) {
	IdentitiesManager identitiesManager = securityConfig.getIdentitiesManager();

	return identitiesManager.removeIdentityLinkFullData(identityKey);
    }
    
    public <T extends PrivateIdentityManager> T getDefaultIdentityManager(Class<T> clazz) throws EventNodeSecurityException {
	T privateIdentityManager = (T) defaultIdentities.get(clazz);
	
	if(privateIdentityManager == null) {
	    /* try to create one from our identities. Use the first one that matches */
	    for(PrivateIdentityData pi: privateIdentities.values()) {
		try{
		   
		    /* see if this identity matches the desired type */
		    privateIdentityManager = (T)(pi);
		    
		    /* add this as default */
		    defaultIdentities.put(clazz, privateIdentityManager);
		    /* break the loop */
		    break;
		    
		}
		catch(Exception e){
		    
		}
	    }
	}
	
	return privateIdentityManager;
    }
    
    public SignatureInfo sign(byte[] data, SpkiPrivateIdentityManager identity, SignCommOperationDef opDef) throws Exception {
	SignOperationWorker signWorker = identity.buildSignWorker(opDef);

        byte[] signature = signWorker.sign(data);

        String publicKey = identity.getPublicNodeIdentity().
                getPublicKey();
        		
        String keyId = cryptoHelper.sha256(publicKey);

        SignatureInfo signatureInfo = new SignatureInfo(keyId,
                cryptoHelper.base64Encode(signature),
                opDef);
        
        return signatureInfo;
    }
    
    public SignatureInfo sign(byte[] data, SignCommOperationDef opDef) throws Exception {
	SpkiPrivateIdentityManager defaultIdentityManager = getDefaultIdentityManager(SpkiPrivateIdentityManager.class);
	return sign(data, defaultIdentityManager, opDef);
    }
    
    public SignatureInfo sign(byte[] data) throws Exception {
	final SignCommOperationDef signCommOperationDef = new SignCommOperationDef();
        signCommOperationDef.setHashingAlgorithm("SHA1withRSA");
        
        return sign(data, signCommOperationDef);
    }

    public EventNodeSecurityConfig getSecurityConfig() {
        return securityConfig;
    }

    public EventNodeLogger getLogger() {
	return nodeConfig.getLogger();
    }
    
}
