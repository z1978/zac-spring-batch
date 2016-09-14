package com.zac.junit;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.StepRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.zac.conf.ZacConfiguration;
import com.zac.spring_batch.user_csv.UserCsvBean;
import com.zac.spring_batch.user_csv.UserCsvConfig;
import com.zac.spring_batch.user_csv.UserCsvProcessor;
import com.zac.spring_batch.user_csv.UserCsvReader;
import com.zac.spring_batch.user_csv.UserCsvWriter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( classes = {ZacConfiguration.class,} )
public class UserCsvBatchTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
	private UserCsvConfig userCsvConfig;
    @Autowired
    private StepRunner stepRunner;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private Step userCsvStep;
    @Autowired
    private UserCsvReader userCsvReader;
    @Autowired
    private UserCsvProcessor userCsvProcessor;
    @Autowired
    private UserCsvWriter userCsvWriter;
    
    @Before
    public void setup() throws SQLException, IOException {
        MockitoAnnotations.initMocks(this);
//        userCsvProcessor.setClient(gEPApiMsgClient);
        
        this.userCsvStep = stepBuilderFactory.get("userCsvStep").<UserCsvBean , UserCsvBean> chunk(10)
                .reader(userCsvReader)
                .processor(userCsvProcessor)
                .writer(userCsvWriter)
                .build();
    }
    @Test
    public void testStep() throws Exception {
        // 単独のStepの実行
        this.stepRunner.launchStep(this.userCsvStep);
    }

    @Test
    public void testJob() throws Exception {
        // Jobの実行 もちろんパラメータ設定もできる
        jobLauncherTestUtils.launchJob(new JobParameters());
    }}