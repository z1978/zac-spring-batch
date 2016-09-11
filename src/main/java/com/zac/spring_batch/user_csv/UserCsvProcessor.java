package com.zac.spring_batch.user_csv;

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

@Component
public class UserCsvProcessor implements ItemProcessor<UserCsvBean, UserCsvBean>{

    private static final Logger log = LoggerFactory.getLogger(UserCsvProcessor.class);

    @Override
    public UserCsvBean process(final UserCsvBean person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final UserCsvBean transformedPerson = new UserCsvBean(firstName, lastName);

        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

}
