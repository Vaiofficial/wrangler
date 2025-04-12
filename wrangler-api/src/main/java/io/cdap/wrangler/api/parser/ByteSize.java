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

public class ByteSize extends Token {
  private final long bytes;

  // Pattern: number followed by unit
  private static final Pattern PATTERN = Pattern.compile("(\\d+)([kKmMgGtT][bB])");

  public ByteSize(String value) {
    super(Type.BYTE_SIZE, value);

    Matcher matcher = PATTERN.matcher(value);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid ByteSize format: " + value);
    }

    long number = Long.parseLong(matcher.group(1));
    String unit = matcher.group(2).toLowerCase();

    switch (unit) {
      case "kb":
        this.bytes = number * 1024L;
        break;
      case "mb":
        this.bytes = number * 1024L * 1024L;
        break;
      case "gb":
        this.bytes = number * 1024L * 1024L * 1024L;
        break;
      case "tb":
        this.bytes = number * 1024L * 1024L * 1024L * 1024L;
        break;
      default:
        throw new IllegalArgumentException("Unsupported ByteSize unit: " + unit);
    }
  }

  public long getBytes() {
    return bytes;
  }
}
