package com.zac.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
public class UserCsvBatchTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

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