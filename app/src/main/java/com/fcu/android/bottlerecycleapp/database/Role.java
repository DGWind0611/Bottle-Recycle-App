package com.fcu.android.bottlerecycleapp.database;

import androidx.annotation.Nullable;

import com.fcu.android.bottlerecycleapp.Gender;

public enum Role {
    USER("一般用戶"),
    ADMIN("管理員") ,
    WORKER("工作人員");

    private final String chineseValue;

    // Enum 的構造方法
    Role(String chineseValue) {
        this.chineseValue = chineseValue;
    }

    // 獲取中文值的方法
    public String getChineseValue() {
        return chineseValue;
    }

    // 根據中文值獲取 Role 枚舉
    @Nullable
    public static Role fromChineseValue(String chineseValue) {
        for (Role role : Role.values()) {
            if (role.getChineseValue().equals(chineseValue)) {
                return role;
            }
        }
        return null; // 或者返回 UNDEFINED 作為預設
    }
}
