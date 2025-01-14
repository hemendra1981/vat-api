/*
 * Copyright 2022 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package v1.models.audit

import play.api.libs.json.{Json, OWrites}
import v1.models.auth.UserDetails

case class AuditDetail(userType: String,
                       agentReferenceNumber: Option[String],
                       response: AuditResponse,
                       `X-CorrelationId`: String,
                       clientId: String)

object AuditDetail {

  implicit val writes: OWrites[AuditDetail] = Json.writes[AuditDetail]

  def apply(userDetails: UserDetails,
            `X-CorrelationId`: String,
            response: AuditResponse): AuditDetail = {

    AuditDetail(
      userType = userDetails.userType,
      agentReferenceNumber = userDetails.agentReferenceNumber,
      response = response,
      `X-CorrelationId` = `X-CorrelationId`,
      clientId = userDetails.clientId
    )
  }
}

case class SubmitAuditDetail(userType: String,
                       agentReferenceNumber: Option[String],
                       response: AuditResponse,
                       `X-CorrelationId`: String,
                       clientId: String,
                       submitRawData: String)

object SubmitAuditDetail {

  implicit val writes: OWrites[SubmitAuditDetail] = Json.writes[SubmitAuditDetail]

  def apply(userDetails: UserDetails,
            `X-CorrelationId`: String,
            response: AuditResponse,
            submitRawData: String): SubmitAuditDetail = {

    SubmitAuditDetail(
      userType = userDetails.userType,
      agentReferenceNumber = userDetails.agentReferenceNumber,
      response = response,
      `X-CorrelationId` = `X-CorrelationId`,
      clientId = userDetails.clientId,
      submitRawData = submitRawData
    )
  }
}
