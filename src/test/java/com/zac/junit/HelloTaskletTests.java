package com.zac.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.concurrent.Callable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@ContextConfiguration(locations = { "/test-context.xml",
        "classpath:/META-INF/spring/batch/hello-tasklet-context.xml",
        "classpath:/META-INF/spring/batch/jdbc-job-context.xml",
        "classpath:/META-INF/spring/integration/hello-integration-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
//测试ITEMREADER/ITEMPROCESSOR/ITEMWRITER时用到
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, 
    StepScopeTestExecutionListener.class })
public class HelloTaskletTests {
    
    @Autowired
    private JobLauncher jobLauncher;
    
    @Autowired
    private Job helloWorldJob;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;//测试JOB/STEP的入口
    
    @Autowired
    private ItemReader xmlReader;
    
    public void testLaunchJobWithJobLauncher() throws Exception {
        JobExecution jobExecution = jobLauncher.run(helloWorldJob, new JobParameters());
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

    /**
     * Create a unique job instance and check it's execution completes
     * successfully - uses the convenience methods provided by the testing
     * superclass.
     */
    @Test
    public void testLaunchJob() throws Exception {

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobLauncherTestUtils.getUniqueJobParameters());
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
    
    public void testIntegration()
    {
        while(true)
        {
            
        }
    }
    
    /**
     * 测试某个STEP
     */
    @Test
    public void testSomeStep()
    {
        JobExecution jobExecution = jobLauncherTestUtils.
                launchStep("xmlFileReadAndWriterStep",getJobParameters());
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
    
    /**
     * 测试READER的方式1时，所需的方法
     * @return
     */
    public StepExecution getStepExecution() {
        StepExecution execution = MetaDataInstanceFactory
                .createStepExecution(getJobParameters());
        return execution;
    }
    
    /**
     * 测试READER的方式1
     * @throws Exception
     */
    @Test
    @DirtiesContext
    public void testReader() throws Exception {
        int count = StepScopeTestUtils.doInStepScope(getStepExecution(),
                new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        int count = 0;
                        try {
                            ((ItemStream) xmlReader)
                                    .open(new ExecutionContext());
                            while (xmlReader.read() != null) {
                                count++;
                            }
                            return count;
                        } finally {
                            ((ItemStream) xmlReader).close();
                        }
                    }
                });
        assertEquals(3, count);
    }
      
    /**
     * 测试READER的方式2
     * @throws UnexpectedInputException
     * @throws ParseException
     * @throws NonTransientResourceException
     * @throws Exception
     */
    @Test
    @DirtiesContext
    public void testReader2() throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception
    {
        assertNotNull(xmlReader.read());
    }
    
    /**
     * 测试READER的方式2时，必须加的方法
     */
    @Before
    public void setUp() {
        ((ItemStream) xmlReader).open(new ExecutionContext());
    }
      
    /**
     * 
     * @return
     */
    private JobParameters getJobParameters() {
        
        String inputFile = "/Users/paul/Documents/PAUL/DOWNLOAD/SOFTWARE/DEVELOP/"
                + "SPRING BATCH/spring-batch-2.1.9.RELEASE/samples/"
                + "spring-batch-simple-cli/file/trades1.xml";
        
        String outputFile = "/Users/paul/Documents/PAUL/DOWNLOAD/SOFTWARE/DEVELOP/"
                + "SPRING BATCH/spring-batch-2.1.9.RELEASE/samples/"
                + "spring-batch-simple-cli/file/output/out.xml";
        
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("input.file.path", inputFile)
                .addString("output.file.path", outputFile)
                .addDate("date", new Date()).toJobParameters();
        
        return jobParameters;
    }


}
