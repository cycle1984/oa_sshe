package cycle.oa_sshe.service.impl;

import org.springframework.stereotype.Service;

import cycle.oa_sshe.base.BaseServiceImpl;
import cycle.oa_sshe.domain.News;
import cycle.oa_sshe.service.NewsService;

@Service("newsService")
public class NewsServiceImpl extends BaseServiceImpl<News> implements NewsService {

}
