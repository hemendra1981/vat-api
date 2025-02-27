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

package v1.services

import play.api.http.{HeaderNames, MimeTypes, Status}
import support.UnitSpec
import uk.gov.hmrc.http.HeaderCarrier
import utils.EndpointLogContext
import v1.mocks.MockIdGenerator

import scala.concurrent.ExecutionContext

trait ServiceSpec extends UnitSpec
  with Status
  with MimeTypes
  with HeaderNames
  with MockIdGenerator {

  implicit val correlationId: String = "a1e8057e-fbbc-47a8-a8b4-78d9f015c253"
  implicit val hc: HeaderCarrier = HeaderCarrier()
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global
  implicit val lc: EndpointLogContext = EndpointLogContext("c", "ep")

}
