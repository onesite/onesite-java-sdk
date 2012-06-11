/**
 * Autogenerated by Thrift Compiler (0.8.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.onesite.sdk.thrift.dao.constants;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum AccountStatus implements org.apache.thrift.TEnum {
  PENDING(0),
  GOOD_STANDING(1),
  DELINQUENT(2),
  INACTIVE(3),
  DISABLED(4),
  DELETED(5);

  private final int value;

  private AccountStatus(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static AccountStatus findByValue(int value) { 
    switch (value) {
      case 0:
        return PENDING;
      case 1:
        return GOOD_STANDING;
      case 2:
        return DELINQUENT;
      case 3:
        return INACTIVE;
      case 4:
        return DISABLED;
      case 5:
        return DELETED;
      default:
        return null;
    }
  }
}