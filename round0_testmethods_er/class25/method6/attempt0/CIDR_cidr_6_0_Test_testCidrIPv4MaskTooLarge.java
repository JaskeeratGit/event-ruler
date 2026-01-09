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

class CIDR_cidr_6_0_Test_testCidrIPv4MaskTooLarge {




    @Test
    void testCidrIPv4MaskTooLarge() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> CIDR.cidr("10.0.0.1/32"));
        assertTrue(ex.getMessage().contains("IPv4 mask bits must be < 32"));
    }





}
