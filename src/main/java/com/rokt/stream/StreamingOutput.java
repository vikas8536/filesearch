package com.rokt.stream;

import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.io.OutputStream;

public class StreamingOutput implements javax.ws.rs.core.StreamingOutput {
    @Override
    public void write(OutputStream outputStream) throws IOException, WebApplicationException {

    }
}
