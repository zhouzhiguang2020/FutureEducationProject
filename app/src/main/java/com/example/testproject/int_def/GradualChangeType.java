package com.example.testproject.int_def;

import androidx.annotation.IntDef;

import com.example.testproject.utils.gradual_change.GradualChangeTextView;

/**
 * @Package: com.example.testproject.int_def
 * @ClassName: GradualChangeType
 * @Author: szj
 * @CreateDate: 8/10/21 9:20 AM
 */
@IntDef({
        GradualChangeTextView.GRADUAL_CHANGE_LEFT,
        GradualChangeTextView.GRADUAL_CHANGE_RIGHT,
})
public @interface GradualChangeType {
}
