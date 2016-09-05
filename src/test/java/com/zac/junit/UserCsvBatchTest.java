package com.zac.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.zac.spring_batch.user_csv.UserCsvConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=UserCsvConfig.class,loader=AnnotationConfigContextLoader.class)
public class UserCsvBatchTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
	private UserCsvConfig userCsvConfig;
    @Test
    public void testStep() throws Exception {
        // 単独のStepの実行
        jobLauncherTestUtils.launchStep("userCsvStep");
    }

    @Test
    public void testJob() throws Exception {
        // Jobの実行 もちろんパラメータ設定もできる
        jobLauncherTestUtils.launchJob(new JobParameters());
    }}