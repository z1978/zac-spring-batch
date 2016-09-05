package com.zac.spring_batch.user_csv;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


/**
 * 
 * @author zac
 * @version
 * @since 2.0.0
 */
@Getter
@Setter
@Component
public class UserCsvProcessor implements ItemProcessor<UserCsvBean, UserCsvBean>{
    private static final Logger logger = LoggerFactory.getLogger(UserCsvProcessor.class);
    
    @Override
    public UserCsvBean process(final UserCsvBean userCsvBean) throws Exception {

        String firstName = userCsvBean.getFirstName();
        String lastName  = userCsvBean.getLastName();
        logger.debug(firstName);
        logger.debug(lastName);

       
        return userCsvBean;
    }

}
