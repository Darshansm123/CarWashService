package utils;
import org.apache.commons.lang3.RandomStringUtils;

public class DataGenerator
{
    public static String generateInvalidMobileNumber() 
    {
        return RandomStringUtils.randomNumeric(9);
    }
}