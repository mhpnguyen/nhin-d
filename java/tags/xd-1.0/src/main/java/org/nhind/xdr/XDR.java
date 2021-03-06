/* 
 * Copyright (c) 2010, NHIN Direct Project
 * All rights reserved.
 *  
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in the 
 *    documentation and/or other materials provided with the distribution.  
 * 3. Neither the name of the the NHIN Direct Project (nhindirect.org)
 *    nor the names of its contributors may be used to endorse or promote products 
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.nhind.xdr;

import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequestType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType;
import java.util.List;
import java.util.UUID;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryErrorList;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;


/**
 *
 * @author vlewis
 */
@WebService(serviceName       = "DocumentRepository_Service",
            portName          = "DocumentRepository_Port_Soap12",
            endpointInterface = "ihe.iti.xds_b._2007.DocumentRepositoryPortType",
            targetNamespace   = "urn:ihe:iti:xds-b:2007",
            wsdlLocation      = "WEB-INF/wsdl/XDS.b_DocumentRepositoryWSDLSynchMTOM.wsdl")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@HandlerChain(file = "/DocumentRepository_Service_handler.xml")
public class XDR extends DocumentRepositoryAbstract{

    /* 
     * (non-Javadoc)
     * 
     * @see org.nhind.xdr.DocumentRepositoryAbstract#documentRepositoryProvideAndRegisterDocumentSetB(ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequestType)
     */
    public RegistryResponseType documentRepositoryProvideAndRegisterDocumentSetB(ProvideAndRegisterDocumentSetRequestType body) {
      RegistryResponseType resp = null;
        try {
            resp = provideAndRegisterDocumentSet(body);
        } catch (Exception x) {
            this.relatesTo = messageId;
            this.action = "urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-bResponse";
            this.messageId = UUID.randomUUID().toString();
            this.to = endpoint;
            setHeaderData();
            resp = new RegistryResponseType();
            resp.setStatus("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure");
            RegistryErrorList rel = new RegistryErrorList();
            String error = x.getMessage();
            rel.setHighestSeverity(error);
            List<RegistryError> rl = rel.getRegistryError();
            RegistryError re = new RegistryError();

            String errorCode = "XDSRegistryError";
            re.setErrorCode(errorCode);
            re.setSeverity("Error");
            re.setCodeContext(error);
            re.setLocation("XDSRepositoryService.java");
            re.setValue(error);
            rl.add(re);
            resp.setRegistryErrorList(rel);
        }
        return resp;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.nhind.xdr.DocumentRepositoryAbstract#documentRepositoryRetrieveDocumentSet(ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType)
     */
    public RetrieveDocumentSetResponseType documentRepositoryRetrieveDocumentSet(RetrieveDocumentSetRequestType body) {
     
        throw new UnsupportedOperationException("Not implemented for XDR");
    }

}
