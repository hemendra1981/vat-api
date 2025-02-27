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

import v1.models.errors._
import v1.models.request.obligations.ObligationsRawData

object ObligationParameterFormatValidation {

  def validate(data: ObligationsRawData): List[MtdError] = data match {

    case ObligationsRawData(_, None, None, Some(status)) =>
      if (status == "O") Nil
      else if (status == "F") List(RuleMissingDateRangeError)
      else List(InvalidStatusError)
    case ObligationsRawData(_, None, None, None) => List(InvalidFromError)
    case ObligationsRawData(_, Some(_), None, _) => List(InvalidToError)
    case ObligationsRawData(_, None, Some(_), _) => List(InvalidFromError)
    case ObligationsRawData(_, Some(from), Some(to), status) =>

      val validList = List(
        DateFormatValidation.validate(from, InvalidFromError),
        DateFormatValidation.validate(to, InvalidToError),
        status.fold(List.empty[MtdError])(s => StatusValidation.validate(s))
      ).filter(_.nonEmpty)

      if (validList.isEmpty) List.empty[MtdError] else validList.head
  }
}
