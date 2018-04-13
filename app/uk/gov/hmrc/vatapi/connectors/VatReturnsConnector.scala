/*
 * Copyright 2018 HM Revenue & Customs
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

package uk.gov.hmrc.vatapi.connectors

import java.net.URLEncoder

import uk.gov.hmrc.domain.Vrn
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.vatapi.BaseConnector
import uk.gov.hmrc.vatapi.config.{AppContext, WSHttp}
import uk.gov.hmrc.vatapi.models.des
import uk.gov.hmrc.vatapi.resources.wrappers.VatReturnResponse

import scala.concurrent.{ExecutionContext, Future}

object VatReturnsConnector extends VatReturnsConnector {
  override val http: WSHttp = WSHttp
}

trait VatReturnsConnector extends BaseConnector {

  def post(vrn: Vrn, vatReturn: des.VatReturnDeclaration)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[VatReturnResponse] = {

    val postUrl: String = s"${AppContext.desUrl}/enterprise/return/vat"

    httpPost[des.VatReturnDeclaration, VatReturnResponse](
      url = s"$postUrl/$vrn",
      elem = vatReturn,
      toResponse = VatReturnResponse
    )
  }

  def query(vrn: Vrn, periodKey: String)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[VatReturnResponse] = {

    val getUrl: String = s"${AppContext.desUrl}/vat/returns/vrn/$vrn?period-key=${URLEncoder.encode(periodKey, "UTF-8")}"

    httpGet[VatReturnResponse](getUrl, VatReturnResponse)
  }

}
