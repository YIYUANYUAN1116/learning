package com.xht.model.params;

import lombok.Data;

import java.util.List;

@Data
public class PdfMergeParam {
    private List<String> mergePaths;
    private String targetPath;
}
