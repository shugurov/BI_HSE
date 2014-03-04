package ru.hse.shugurov;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Иван on 03.03.14.
 */
public class FileCache
{
    private static FileCache fileCache;

    private File cacheDirectory;

    private FileCache(Context context)
    {
        cacheDirectory = context.getCacheDir();
    }

    public static void init(Context context)
    {
        fileCache = new FileCache(context);
    }

    public static boolean isInitiated()
    {
        return fileCache != null;
    }

    public static FileCache instance()
    {
        return fileCache;
    }

    public void add(String key, String value)
    {
        File fileToAdd = new File(cacheDirectory, key);
        try
        {
            FileOutputStream fileOutputStream = null;
            try
            {
                fileOutputStream = new FileOutputStream(fileToAdd);
                PrintWriter printWriter = new PrintWriter(fileOutputStream);
                printWriter.print(value);
                printWriter.flush();
            } finally
            {
                if (fileOutputStream != null)
                {
                    fileOutputStream.close();
                }
            }

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String get(String key)
    {
        File fileToRead = new File(cacheDirectory, key);
        if (fileToRead.exists())
        {
            StringBuilder builder = new StringBuilder();
            Scanner scanner = null;
            try
            {
                try
                {
                    scanner = new Scanner(fileToRead);
                    while (scanner.hasNextLine())
                    {
                        builder.append(scanner.nextLine());
                    }
                } finally
                {
                    if (scanner != null)
                    {
                        scanner.close();
                    }
                }

            } catch (FileNotFoundException e)
            {
                return null;
            }
            return builder.toString();
        } else
        {
            return null;
        }
    }

    public void remove(String key)
    {
        File fileToBeRemoved = new File(cacheDirectory, key);
        fileToBeRemoved.delete();
    }
}
