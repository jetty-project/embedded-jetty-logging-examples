//
//  ========================================================================
//  Copyright (c) Mort Bay Consulting Pty Ltd and others.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

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
