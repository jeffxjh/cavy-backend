package com.jh.cavy.favour.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jh.cavy.favour.ao.FavourInoutAO;
import com.jh.cavy.favour.ao.FavourRecordAO;
import com.jh.cavy.favour.domain.FavourRecord;
import com.jh.cavy.favour.vo.FavourInoutHeadVO;
import com.jh.cavy.favour.vo.FavourInoutPageVO;
import com.jh.cavy.favour.vo.FavourRecordVO;

/**
* @author jeffx
* @description 针对表【t_bus_favour_record(人情管理-人情记录表)】的数据库操作Service
* @createDate 2024-09-20 22:36:16
*/
public interface FavourRecordService extends IService<FavourRecord> {

    Page<FavourInoutPageVO> inoutPage(FavourInoutAO favourInoutAO);

    FavourInoutHeadVO inoutHead(FavourInoutHeadVO favourInoutHeadVO);

    FavourRecordVO updateRecord(FavourRecordAO favourRecordAO);

    FavourRecordVO addRecord(FavourRecordAO favourRecordAO);
}
