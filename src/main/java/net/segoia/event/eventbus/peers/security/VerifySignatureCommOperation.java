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

public class VerifySignatureCommOperation
	implements CommOperation<SignCommOperationOutput, VerifySignatureOperationContext, OperationOutput> {

    @Override
    public OperationOutput operate(
	    OperationDataContext<SignCommOperationOutput, VerifySignatureOperationContext> dataContext)
	    throws GenericOperationException {
	VerifySignatureOperationContext context = dataContext.getOpContext();

	boolean isValid;
	SignCommOperationOutput sigData = null;
	try {
	    sigData = context.deserializeTo(SignCommOperationOutput.class, dataContext.getInputData());
	    isValid = context.verify(sigData);

	} catch (Throwable e) {
	    throw new CommOperationException("Failed to verify signature", e, context);
	}
	if (!isValid) {
	    throw new SignatureInvalidException(context, context.getPeerIdentity());
	}
	
	return new OperationOutput(sigData.getData());
    }

}
