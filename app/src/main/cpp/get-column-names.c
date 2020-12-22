#include <jni.h>

JNIEXPORT jobjectArray JNICALL
Java_id_ac_ui_cs_mobileprogramming_farhanazyumardhiazmi_coffeepedia_util_CoffeeBeanPdfExportable_getColumnNamesNative(
    JNIEnv* env,
    jobject thiz
) {
    jobjectArray returnValue;
    int length = 8;
    char *columnNames[] = {
        "Id",
        "Name",
        "Type",
        "Origin",
        "Altitude",
        "Process",
        "Aroma",
        "Taste Note"
    };
    returnValue = (*env)->NewObjectArray(
        env,
        length,
        (*env)->FindClass(env, "java/lang/String"),
        0
    );
    for (int i = 0; i < length; i++) {
        (*env)->SetObjectArrayElement(
            env,
            returnValue,
            i,
            (*env)->NewStringUTF(env, columnNames[i])
        );
    }

    return returnValue;
}


JNIEXPORT jobjectArray JNICALL
Java_id_ac_ui_cs_mobileprogramming_farhanazyumardhiazmi_coffeepedia_util_BrewMethodPdfExportable_getColumnNamesNative(
    JNIEnv* env,
    jobject thiz
) {
    jobjectArray returnValue;
    int length = 3;
    char *columnNames[] = {
        "Id",
        "Name",
        "Description"
    };
    returnValue = (*env)->NewObjectArray(
        env,
        length,
        (*env)->FindClass(env, "java/lang/String"),
        0
    );
    for (int i = 0; i < length; i++) {
        (*env)->SetObjectArrayElement(
            env,
            returnValue,
            i,
            (*env)->NewStringUTF(env, columnNames[i])
        );
    }

    return returnValue;
}


JNIEXPORT jobjectArray JNICALL
Java_id_ac_ui_cs_mobileprogramming_farhanazyumardhiazmi_coffeepedia_util_BrewRecipePdfExportable_getColumnNamesNative(
    JNIEnv* env,
    jobject thiz
) {
    jobjectArray returnValue;
    int length = 7;
    char *columnNames[] = {
        "Id",
        "Name",
        "Roast Level",
        "Grind Level",
        "Water Temperature",
        "Brew Time",
        "Brew Steps"
    };
    returnValue = (*env)->NewObjectArray(
        env,
        length,
        (*env)->FindClass(env, "java/lang/String"),
        0
    );
    for (int i = 0; i < length; i++) {
        (*env)->SetObjectArrayElement(
            env,
            returnValue,
            i,
            (*env)->NewStringUTF(env, columnNames[i])
        );
    }

    return returnValue;
}
