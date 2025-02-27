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

package v1.controllers.requestParsers

import support.UnitSpec
import v1.models.domain.Vrn
import v1.mocks.validators.MockViewReturnValidator
import v1.models.errors.{ErrorWrapper, PeriodKeyFormatError, VrnFormatError}
import v1.models.request.viewReturn.{ViewRawData, ViewRequest}

class ViewReturnRequestParserSpec extends UnitSpec {

  trait Test extends MockViewReturnValidator {
    lazy val parser = new ViewReturnRequestParser(mockValidator)
  }

  private val validVrn = "AA111111A"
  private val validPeriodKey = "F02LDPDEE"
  private val invalidVrn = "notAVrn"
  private val invalidPeriodKey = "notAperiodKey"
  implicit val correlationId: String = "a1e8057e-fbbc-47a8-a8b4-78d9f015c253"

  "parsing a retrieve periodKey request" should {
    "return a retrieve periodKey request" when {
      "valid data is provided" in new Test {

        MockViewReturnValidator.validate(ViewRawData(validVrn, validPeriodKey))
          .returns(Nil)

        parser.parseRequest(ViewRawData(validVrn, validPeriodKey)) shouldBe
          Right(ViewRequest(Vrn(validVrn), validPeriodKey))
      }
    }
  }

  "return an error" when {
    "invalid data is provided" in new Test{
      MockViewReturnValidator.validate(ViewRawData(invalidVrn, validPeriodKey))
        .returns(List(VrnFormatError))

      parser.parseRequest(ViewRawData(invalidVrn, validPeriodKey)) shouldBe
        Left(ErrorWrapper(correlationId, VrnFormatError, None))
    }
  }

  "return BadRequest wrapped error" when {
    "invalid period key is provided" in new Test{
      MockViewReturnValidator.validate(ViewRawData(validVrn, invalidPeriodKey))
        .returns(List(PeriodKeyFormatError))

      parser.parseRequest(ViewRawData(validVrn, invalidPeriodKey)) shouldBe
        Left(ErrorWrapper(correlationId, PeriodKeyFormatError, None))
    }
  }

  "return only single error" when {
    "multiple request parameters supplied are invalid" in new Test{
      MockViewReturnValidator.validate(ViewRawData(invalidVrn, invalidPeriodKey))
        .returns(List(VrnFormatError))

      parser.parseRequest(ViewRawData(invalidVrn, invalidPeriodKey)) shouldBe
        Left(ErrorWrapper(correlationId, VrnFormatError, None))
    }
  }
}
