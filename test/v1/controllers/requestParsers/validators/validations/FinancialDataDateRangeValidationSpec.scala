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

package v1.controllers.requestParsers.validators.validations

import support.UnitSpec
import v1.models.errors.FinancialDataInvalidDateRangeError

class FinancialDataDateRangeValidationSpec extends UnitSpec {

  val from2020 = "2020-01-01"
  val to2021 = "2020-12-31"
  val to2021Plus1Day = "2021-01-01"
  val from2018 = "2018-04-05"
  val to2019 = "2019-02-20"

  "validate" should {
    "return an empty list" when {

      "passed valid from and to dates" in {
        FinancialDataDateRangeValidation.validate(from2020, to2021) shouldBe List()
      }

    }
    "return a list containing an error" when {
      "passed an invalid date range" in {
        FinancialDataDateRangeValidation.validate(from2020, to2021Plus1Day) shouldBe List(FinancialDataInvalidDateRangeError)
      }

    }
  }

}
