package com.hdsxtech.tjbhzc_datarecp_tcp.util;

public class CRCUtil {
    static  int      crctab[]           = {
            0, 4129, 8258, 12387, 16516, 20645, 24774, 28903, 33032, 37161,
            41290, 45419, 49548, 53677, 57806, 61935, 4657, 528, 12915, 8786,
            21173, 17044, 29431, 25302, 37689, 33560, 45947, 41818, 54205, 50076,
            62463, 58334, 9314, 13379, 1056, 5121, 25830, 29895, 17572, 21637,
            42346, 46411, 34088, 38153, 58862, 62927, 50604, 54669, 13907, 9842,
            5649, 1584, 30423, 26358, 22165, 18100, 46939, 42874, 38681, 34616,
            63455, 59390, 55197, 51132, 18628, 22757, 26758, 30887, 2112, 6241,
            10242, 14371, 51660, 55789, 59790, 63919, 35144, 39273, 43274, 47403,
            23285, 19156, 31415, 27286, 6769, 2640, 14899, 10770, 56317, 52188,
            64447, 60318, 39801, 35672, 47931, 43802, 27814, 31879, 19684, 23749,
            11298, 15363, 3168, 7233, 60846, 64911, 52716, 56781, 44330, 48395,
            36200, 40265, 32407, 28342, 24277, 20212, 15891, 11826, 7761, 3696,
            65439, 61374, 57309, 53244, 48923, 44858, 40793, 36728, 37256, 33193,
            45514, 41451, 53516, 49453, 61774, 57711, 4224, 161, 12482, 8419,
            20484, 16421, 28742, 24679, 33721, 37784, 41979, 46042, 49981, 54044,
            58239, 62302, 689, 4752, 8947, 13010, 16949, 21012, 25207, 29270,
            46570, 42443, 38312, 34185, 62830, 58703, 54572, 50445, 13538, 9411,
            5280, 1153, 29798, 25671, 21540, 17413, 42971, 47098, 34713, 38840,
            59231, 63358, 50973, 55100, 9939, 14066, 1681, 5808, 26199, 30326,
            17941, 22068, 55628, 51565, 63758, 59695, 39368, 35305, 47498, 43435,
            22596, 18533, 30726, 26663, 6336, 2273, 14466, 10403, 52093, 56156,
            60223, 64286, 35833, 39896, 43963, 48026, 19061, 23124, 27191, 31254,
            2801, 6864, 10931, 14994, 64814, 60687, 56684, 52557, 48554, 44427,
            40424, 36297, 31782, 27655, 23652, 19525, 15522, 11395, 7392, 3265,
            61215, 65342, 53085, 57212, 44955, 49082, 36825, 40952, 28183, 32310,
            20053, 24180, 11923, 16050, 3793, 7920
    };
    static  short[]  auchCRCHi          = new short[]{(short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64};
    static  short[]  auchCRCLo          = new short[]{(short) 0, (short) 192, (short) 193, (short) 1, (short) 195, (short) 3, (short) 2, (short) 194, (short) 198, (short) 6, (short) 7, (short) 199, (short) 5, (short) 197, (short) 196, (short) 4, (short) 204, (short) 12, (short) 13, (short) 205, (short) 15, (short) 207, (short) 206, (short) 14, (short) 10, (short) 202, (short) 203, (short) 11, (short) 201, (short) 9, (short) 8, (short) 200, (short) 216, (short) 24, (short) 25, (short) 217, (short) 27, (short) 219, (short) 218, (short) 26, (short) 30, (short) 222, (short) 223, (short) 31, (short) 221, (short) 29, (short) 28, (short) 220, (short) 20, (short) 212, (short) 213, (short) 21, (short) 215, (short) 23, (short) 22, (short) 214, (short) 210, (short) 18, (short) 19, (short) 211, (short) 17, (short) 209, (short) 208, (short) 16, (short) 240, (short) 48, (short) 49, (short) 241, (short) 51, (short) 243, (short) 242, (short) 50, (short) 54, (short) 246, (short) 247, (short) 55, (short) 245, (short) 53, (short) 52, (short) 244, (short) 60, (short) 252, (short) 253, (short) 61, (short) 255, (short) 63, (short) 62, (short) 254, (short) 250, (short) 58, (short) 59, (short) 251, (short) 57, (short) 249, (short) 248, (short) 56, (short) 40, (short) 232, (short) 233, (short) 41, (short) 235, (short) 43, (short) 42, (short) 234, (short) 238, (short) 46, (short) 47, (short) 239, (short) 45, (short) 237, (short) 236, (short) 44, (short) 228, (short) 36, (short) 37, (short) 229, (short) 39, (short) 231, (short) 230, (short) 38, (short) 34, (short) 226, (short) 227, (short) 35, (short) 225, (short) 33, (short) 32, (short) 224, (short) 160, (short) 96, (short) 97, (short) 161, (short) 99, (short) 163, (short) 162, (short) 98, (short) 102, (short) 166, (short) 167, (short) 103, (short) 165, (short) 101, (short) 100, (short) 164, (short) 108, (short) 172, (short) 173, (short) 109, (short) 175, (short) 111, (short) 110, (short) 174, (short) 170, (short) 106, (short) 107, (short) 171, (short) 105, (short) 169, (short) 168, (short) 104, (short) 120, (short) 184, (short) 185, (short) 121, (short) 187, (short) 123, (short) 122, (short) 186, (short) 190, (short) 126, (short) 127, (short) 191, (short) 125, (short) 189, (short) 188, (short) 124, (short) 180, (short) 116, (short) 117, (short) 181, (short) 119, (short) 183, (short) 182, (short) 118, (short) 114, (short) 178, (short) 179, (short) 115, (short) 177, (short) 113, (short) 112, (short) 176, (short) 80, (short) 144, (short) 145, (short) 81, (short) 147, (short) 83, (short) 82, (short) 146, (short) 150, (short) 86, (short) 87, (short) 151, (short) 85, (short) 149, (short) 148, (short) 84, (short) 156, (short) 92, (short) 93, (short) 157, (short) 95, (short) 159, (short) 158, (short) 94, (short) 90, (short) 154, (short) 155, (short) 91, (short) 153, (short) 89, (short) 88, (short) 152, (short) 136, (short) 72, (short) 73, (short) 137, (short) 75, (short) 139, (short) 138, (short) 74, (short) 78, (short) 142, (short) 143, (short) 79, (short) 141, (short) 77, (short) 76, (short) 140, (short) 68, (short) 132, (short) 133, (short) 69, (short) 135, (short) 71, (short) 70, (short) 134, (short) 130, (short) 66, (short) 67, (short) 131, (short) 65, (short) 129, (short) 128, (short) 64};


    public static String GetSmallEndCRC(int[] aIntTrafficData) {
        byte uchCRCHi = -1;
        byte uchCRCLo = -1;
        int l = aIntTrafficData.length;

        for (int i = 0; i < l; ++i) {
            byte uIndex = (byte) (uchCRCLo ^ (byte) (aIntTrafficData[i] & 255));
            uchCRCLo = (byte) (uchCRCHi ^ (byte) (auchCRCHi[uIndex & 255] & 255));
            uchCRCHi = (byte) (auchCRCLo[uIndex & 255] & 255);
        }

        String tempStr = String.format("%04x", new Object[]{Integer.valueOf(uchCRCHi << 8 & '\uffff' | uchCRCLo & 255)});
        return tempStr.substring(2, 4) + tempStr.substring(0, 2);
    }

    public static String GetSmallEndCRC(byte[] aIntTrafficData) {
        byte uchCRCHi = -1;
        byte uchCRCLo = -1;
        int l = aIntTrafficData.length;

        for (int i = 0; i < l; ++i) {
            byte uIndex = (byte) (uchCRCLo ^ (byte) (aIntTrafficData[i] & 255));
            uchCRCLo = (byte) (uchCRCHi ^ (byte) (auchCRCHi[uIndex & 255] & 255));
            uchCRCHi = (byte) (auchCRCLo[uIndex & 255] & 255);
        }

        String tempStr = String.format("%04x", new Object[]{Integer.valueOf(uchCRCHi << 8 & '\uffff' | uchCRCLo & 255)});
        return tempStr.substring(2, 4) + tempStr.substring(0, 2);
    }


}
