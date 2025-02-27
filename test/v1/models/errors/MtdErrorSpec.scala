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

package v1.models.errors

import play.api.libs.json.{JsValue, Json}
import support.UnitSpec

class MtdErrorSpec extends UnitSpec {

  "MtdError" when {
    "written to JSON" should {
      "generate the correct JSON" in {
        Json.toJson(MtdError("CODE", "some message")) shouldBe Json.parse(
          """
            |{
            |   "code": "CODE",
            |   "message": "some message"
            |}
        """.stripMargin
        )
      }
    }
  }

  "written to JSON with custom JSON" should {
    "generate the correct JSON" in {

      val customJson: JsValue = Json.parse(
        """
          |{
          |  "statusCode": "STATUS_CODE",
          |  "errorMessage": "ERROR_MESSAGE"
          |}
        """.stripMargin
      )

      Json.toJson(MtdError("CODE", "some message", Some(customJson))) shouldBe customJson
    }
  }
}
