package org.eclipse.jetty.demo;

import java.util.LinkedList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.Assert;

public class CaptureHandler extends Handler
{
    public static CaptureHandler attach(String logname)
    {
        CaptureHandler handler = new CaptureHandler();
        Logger.getLogger(logname).addHandler(handler);
        return handler;
    }

    private LinkedList<LogRecord> events = new LinkedList<>();

    @Override
    public void publish(LogRecord record)
    {
        events.add(record);
    }

    @Override
    public void flush()
    {
    }

    @Override
    public void close() throws SecurityException
    {
    }

    public void detach(String logname)
    {
        Logger.getLogger(logname).removeHandler(this);
    }

    public void assertContainsRecord(String logname, String containsString)
    {
        for (LogRecord record : events)
        {
            if (record.getLoggerName().startsWith(logname))
            {
                if (record.getMessage().contains(containsString))
                {
                    // found it
                    return;
                }
            }
        }
        Assert.fail("Unable to find record matching logname [" + logname + "] containing string [" + containsString + "]");
    }
}
