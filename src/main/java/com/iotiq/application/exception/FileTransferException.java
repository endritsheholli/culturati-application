package com.iotiq.application.exception;

import com.iotiq.commons.exceptions.ApplicationException;
import com.iotiq.commons.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class FileTransferException extends ApplicationException {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    public FileTransferException(String absolutePath, Throwable e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "fileTransferException", Collections.emptyList(), new String[]{absolutePath});

        log.error("Error occurred while transferring file ", e);
    }
}
