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
import v1.mocks.validators.MockLiabilitiesValidator
import v1.models.errors._
import v1.models.request.liabilities.{LiabilitiesRawData, LiabilitiesRequest}

class LiabilitiesRequestParserSpec extends UnitSpec {

  trait Test extends MockLiabilitiesValidator {
    lazy val parser = new LiabilitiesRequestParser(mockValidator)
  }

  implicit val correlationId: String = "a1e8057e-fbbc-47a8-a8b4-78d9f015c253"
  private val validVrn = "123456789"
  private val validFromDate = "2020-01-01"
  private val validToDate = "2020-03-31"

  private val invalidVrn = "NotAValidVrn"
  private val invalidFrom = "2019-79-35"
  private val invalidTo = "2018-98-03"

  "parsing a retrieve Liability request" should {
    "return a retrieve Liability request" when {
      "valid data is provided" in new Test {

        MockVrnValidator.validate(LiabilitiesRawData(validVrn, Some(validFromDate), Some(validToDate))).returns(Nil)

        parser.parseRequest(LiabilitiesRawData(validVrn, Some(validFromDate), Some(validToDate))) shouldBe
          Right(LiabilitiesRequest(Vrn(validVrn), validFromDate, validToDate))
      }
    }

    "return an error" when {
      "invalid VRN is provided" in new Test {
        MockVrnValidator.validate(LiabilitiesRawData(invalidVrn, Some(validFromDate), Some(validToDate))).returns(List(VrnFormatError))

        parser.parseRequest(LiabilitiesRawData(invalidVrn, Some(validFromDate), Some(validToDate))) shouldBe
          Left(ErrorWrapper(correlationId, VrnFormatError, None))
      }

      "invalid from date is provided" in new Test {
        MockVrnValidator.validate(LiabilitiesRawData(validVrn, Some(invalidFrom), Some(validToDate)))
          .returns(List(FinancialDataInvalidDateFromError))

        parser.parseRequest(LiabilitiesRawData(validVrn, Some(invalidFrom), Some(validToDate))) shouldBe
          Left(ErrorWrapper(correlationId, FinancialDataInvalidDateFromError, None))
      }

      "invalid to date is provided" in new Test {
        MockVrnValidator.validate(LiabilitiesRawData(validVrn, Some(validFromDate), Some(invalidTo)))
          .returns(List(FinancialDataInvalidDateToError))

        parser.parseRequest(LiabilitiesRawData(validVrn, Some(validFromDate), Some(invalidTo))) shouldBe
          Left(ErrorWrapper(correlationId, FinancialDataInvalidDateToError, None))
      }

      "invalid date range is provided" in new Test {
        MockVrnValidator.validate(LiabilitiesRawData(validVrn, Some("2017-01-01"), Some("2019-01-01")))
          .returns(List(FinancialDataInvalidDateRangeError))

        parser.parseRequest(LiabilitiesRawData(validVrn, Some("2017-01-01"), Some("2019-01-01"))) shouldBe
          Left(ErrorWrapper(correlationId, FinancialDataInvalidDateRangeError, None))
      }
    }
  }
}
