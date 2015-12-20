package cycle.oa_sshe.service.impl;

import org.springframework.stereotype.Service;

import cycle.oa_sshe.domain.MyFile;
import cycle.oa_sshe.base.BaseServiceImpl;
import cycle.oa_sshe.service.MyFileService;

@Service("myFileService")
public class MyFileServiceImpl extends BaseServiceImpl<MyFile> implements
		MyFileService {

}
