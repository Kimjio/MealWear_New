package com.kimjio.mealwear;

import org.hyunjun.school.School;
import org.hyunjun.school.SchoolException;

/**
 * Created by kimji on 2017-11-12.
 */

public class SchoolConverter {
    public static School.Type IntToType(int schoolType) throws SchoolException {
        switch (schoolType) {
            case 1:
                return School.Type.KINDERGARTEN;
            case 2:
                return School.Type.ELEMENTARY;
            case 3:
                return School.Type.MIDDLE;
            case 4:
                return School.Type.HIGH;
            default:
                throw new SchoolException("올바르지 않은 값.");
        }
    }

    public static School.Region IntToRegion(int schoolRegion) throws SchoolException {
        switch (schoolRegion) {
            case 0:
                return School.Region.SEOUL;
            case 1:
                return School.Region.BUSAN;
            case 2:
                return School.Region.DAEGU;
            case 3:
                return School.Region.INCHEON;
            case 4:
                return School.Region.GWANGJU;
            case 5:
                return School.Region.DAEJEON;
            case 6:
                return School.Region.ULSAN;
            case 7:
                return School.Region.SEJONG;
            case 8:
                return School.Region.GYEONGGI;
            case 9:
                return School.Region.KANGWON;
            case 10:
                return School.Region.CHUNGBUK;
            case 11:
                return School.Region.CHUNGNAM;
            case 12:
                return School.Region.JEONBUK;
            case 13:
                return School.Region.JEONNAM;
            case 14:
                return School.Region.GYEONGBUK;
            case 15:
                return School.Region.GYEONGNAM;
            case 16:
                return School.Region.JEJU;
            default:
                throw new SchoolException("올바르지 않은 값.");
        }
    }
}
