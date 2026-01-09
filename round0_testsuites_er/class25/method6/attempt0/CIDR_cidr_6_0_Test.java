package software.amazon.event.ruler;

import org.junit.jupiter.api.function.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static software.amazon.event.ruler.Constants.HEX_DIGITS;

class CIDR_cidr_6_0_Test {

    @Test
    void testCidrMalformedNoSlash() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> CIDR.cidr("192.168.0.1"));
        assertTrue(ex.getMessage().contains("one '/' required"));
    }

    @Test
    void testCidrMaskNotInteger() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> CIDR.cidr("192.168.0.1/abc"));
        assertTrue(ex.getMessage().contains("mask bits must be an integer"));
    }

    @Test
    void testCidrNegativeMask() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> CIDR.cidr("192.168.0.1/-1"));
        assertTrue(ex.getMessage().contains("must not be negative"));
    }

    @Test
    void testCidrIPv4MaskTooLarge() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> CIDR.cidr("10.0.0.1/32"));
        assertTrue(ex.getMessage().contains("IPv4 mask bits must be < 32"));
    }

    @Test
    void testCidrIPv6MaskTooLarge() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> CIDR.cidr("2001:db8::1/128"));
        assertTrue(ex.getMessage().contains("IPv6 mask bits must be < 128"));
    }

    @Test
    void testCidrValidIPv4ReturnsRange() {
        // Should not throw and should return a non-null Range
        Range r = CIDR.cidr("192.168.0.1/24");
        assertNotNull(r);
    }

    @Test
    void testCidrValidIPv6ReturnsRange() {
        Range r = CIDR.cidr("2001:db8::1/32");
        assertNotNull(r);
    }

    @Test
    void testPrivateIpToBytesAndComputeBoundsAndHexDigits() throws Exception {
        // Use reflection to call private methods: ipToBytes, computeBottomBytes, computeTopBytes, toHexDigits
        Method ipToBytes = CIDR.class.getDeclaredMethod("ipToBytes", String.class);
        ipToBytes.setAccessible(true);
        Method computeBottom = CIDR.class.getDeclaredMethod("computeBottomBytes", byte[].class, int.class);
        computeBottom.setAccessible(true);
        Method computeTop = CIDR.class.getDeclaredMethod("computeTopBytes", byte[].class, int.class);
        computeTop.setAccessible(true);
        Method toHexDigits = CIDR.class.getDeclaredMethod("toHexDigits", byte[].class);
        toHexDigits.setAccessible(true);
        byte[] base = (byte[]) ipToBytes.invoke(null, "192.168.0.1");
        assertEquals(4, base.length);
        // check base matches InetAddress representation
        assertArrayEquals(InetAddress.getByName("192.168.0.1").getAddress(), base);
        // For /24 on IPv4, variable bits = 8 -> last byte variable -> min => last byte 0, max => last byte 255
        byte[] minBytes = (byte[]) computeBottom.invoke(null, base, 24);
        byte[] maxBytes = (byte[]) computeTop.invoke(null, base, 24);
        assertArrayEquals(new byte[] { (byte) 192, (byte) 168, (byte) 0, (byte) 0 }, minBytes);
        assertArrayEquals(new byte[] { (byte) 192, (byte) 168, (byte) 0, (byte) 0xFF }, maxBytes);
        byte[] minHex = (byte[]) toHexDigits.invoke(null, minBytes);
        byte[] maxHex = (byte[]) toHexDigits.invoke(null, maxBytes);
        String minHexStr = new String(minHex, StandardCharsets.UTF_8).toLowerCase();
        String maxHexStr = new String(maxHex, StandardCharsets.UTF_8).toLowerCase();
        assertEquals("c0a80000", minHexStr);
        assertEquals("c0a800ff", maxHexStr);
    }

    @Test
    void testIpToBytesInvalidInputThrows() throws Exception {
        Method ipToBytes = CIDR.class.getDeclaredMethod("ipToBytes", String.class);
        ipToBytes.setAccessible(true);
        InvocationTargetException thrown = assertThrows(InvocationTargetException.class, () -> ipToBytes.invoke(null, "not-an-ip"));
        // The underlying cause should be IllegalArgumentException with a message indicating invalid or nonstandard IP
        Throwable cause = thrown.getCause();
        assertNotNull(cause);
        assertTrue(cause instanceof IllegalArgumentException);
        assertTrue(cause.getMessage().toLowerCase().contains("invalid ip") || cause.getMessage().toLowerCase().contains("nonstandard ip"));
    }
}
