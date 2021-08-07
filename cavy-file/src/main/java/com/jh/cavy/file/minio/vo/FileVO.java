
package com.jh.cavy.file.minio.vo;

import java.util.List;

public class FileVO {

    private List<Datum> data;
    private Long errno;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Long getErrno() {
        return errno;
    }

    public void setErrno(Long errno) {
        this.errno = errno;
    }

}
