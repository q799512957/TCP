//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hdsxtech.tjbhzc_datarecp_tcp.server;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface HKSPDll extends Library {
    HKSPDll instanceDll = (HKSPDll)Native.loadLibrary("HKSPDLL", HKSPDll.class);

    int transform(String var1);

    int transbyte(String var1, byte[] var2, int var3);
}
