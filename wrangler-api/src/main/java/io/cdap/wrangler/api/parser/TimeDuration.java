/*
 * Copyright © 2024 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */


package io.cdap.wrangler.api.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeDuration extends Token {
  private final long milliseconds;

  // Pattern: number followed by unit
  private static final Pattern PATTERN = Pattern.compile("(\\d+)([smhdSMHD])");

  public TimeDuration(String value) {
    super(Type.TIME_DURATION, value);

    Matcher matcher = PATTERN.matcher(value);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid TimeDuration format: " + value);
    }

    long number = Long.parseLong(matcher.group(1));
    char unit = Character.toLowerCase(matcher.group(2).charAt(0));

    switch (unit) {
      case 's':
        this.milliseconds = number * 1000L;
        break;
      case 'm':
        this.milliseconds = number * 60L * 1000L;
        break;
      case 'h':
        this.milliseconds = number * 60L * 60L * 1000L;
        break;
      case 'd':
        this.milliseconds = number * 24L * 60L * 60L * 1000L;
        break;
      default:
        throw new IllegalArgumentException("Unsupported TimeDuration unit: " + unit);
    }
  }

  public long getMilliseconds() {
    return milliseconds;
  }
}
