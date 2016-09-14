package com.zac.junit;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.StepRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.zac.conf.ZacConfiguration;
import com.zac.spring.boot.example.PersonBean;
import com.zac.spring.boot.example.PersonItemProcessor;
import com.zac.spring_batch.user_csv.UserCsvBean;
import com.zac.spring_batch.user_csv.UserCsvConfig;
import com.zac.spring_batch.user_csv.UserCsvProcessor;
import com.zac.spring_batch.user_csv.UserCsvReader;
import com.zac.spring_batch.user_csv.UserCsvWriter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ZacConfiguration.class, })
public class SpringBootBatchTest {

	private static final Logger log = LoggerFactory.getLogger(SpringBootBatchTest.class);

	private static EmbeddedDatabase testDataBase;

	private static ScriptRunner scriptRunner;

	@ClassRule
	public static WireMockClassRule wireMockRule;

	private static ExecutionContext ctxt;

	@Rule
	public WireMockClassRule instanceRule = wireMockRule;

	@Autowired
	private StepRunner stepRunner;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	@Qualifier("zacReader")
	private MyBatisPagingItemReader<PersonBean> zacReader;
	
	@Autowired
	private PersonItemProcessor personItemProcessor;
	
	@Autowired
	private Step zacStep;
	
	//@Autowired
		private Step sendLabelStep;
		
		
	@BeforeClass
	public static void init() throws SQLException, IOException {
		testDataBase = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
				.continueOnError(true)
				.ignoreFailedDrops(true)
				.build();
		
		wireMockRule = new WireMockClassRule(18088);
		log.debug("Init starts");
		scriptRunner = new ScriptRunner(testDataBase.getConnection());
		prepareData("sql/zac_create_schema.sql");
		log.debug("Init finished");
	}
	
	private static void prepareData(String classPathUrl) throws IOException {
		scriptRunner.runScript(new InputStreamReader(new ClassPathResource(classPathUrl).getInputStream()));
//		scriptRunner.closeConnection();
	}
	
	@Before
	public void setup() throws SQLException, IOException {
		MockitoAnnotations.initMocks(this);
		cleanTables();
		
		this.zacStep = stepBuilderFactory.get("step1").<PersonBean , PersonBean> chunk(10)
                .reader(zacReader)
                .processor(personItemProcessor)
                .build();
	}
	
	private static void cleanTables() throws IOException {
		scriptRunner.runScript(new InputStreamReader(new ClassPathResource("org/springframework/batch/core/schema-drop-mysql.sql").getInputStream()));	
		scriptRunner.runScript(new InputStreamReader(new ClassPathResource("sql/truncatetable.sql").getInputStream()));
	}
	
	
	/**
	 * to db.
	 */
	@Test
	public void test01() {
		System.err.println("test01 started...");
		
		ctxt = new ExecutionContext();
		this.stepRunner.launchStep(zacStep, ctxt);
	
	}


	// @ClassRule
	// public static WireMockClassRule wireMockRule;
	//
	// @Rule
	// public WireMockClassRule instanceRule = wireMockRule;
	//
	// private static EmbeddedDatabase testDataBase;
	//
	// private static ScriptRunner scriptRunner;
	//
	// @SuppressWarnings("unused")
	// private static ExecutionContext executionContext;
	//
	// private static JobLauncherTestUtils jobLauncherTestUtils;
	//
	// @SuppressWarnings("unused")
	// private static JobExecution jobExecution;
	//
	// @SuppressWarnings("unused")
	// private static JobParameters jobParameters;
	//
	// @SuppressWarnings("unused")
	// private static Pattern dbBean2StrPattern;
	//
	// @Autowired
	// private Step testStep;

	// @BeforeClass
	// public static void execBeforeClass() throws SQLException{
	// System.err.println("execBeforeClass START");
	// // Init
	// // testDataBase = new
	// EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
	// // .continueOnError(true)
	// // .addScript("classpath:gep_sgw_db_schema.sql")
	// // .ignoreFailedDrops(true)
	// // .build();
	// //
	// // wireMockRule = new WireMockClassRule(18088);
	//
	//
	// // scriptRunner = new ScriptRunner(testDataBase.getConnection());
	// // executionContext = new ExecutionContext();
	// //
	// // dbBean2StrPattern = Pattern.compile("^\\S+@\\w+\\[(.+)\\]$",
	// Pattern.CASE_INSENSITIVE);
	//
	// System.err.println("execBeforeClass END");
	// }

	// @BeforeClass
	// public static void execBeforeClass() {
	// System.out.println("*** BeforeClass.");
	// }
	// @Before
	// public void execBefore() {
	// System.out.println("*.Before.");
	// }
	//
	// @Test
	// public void hogeHogeTest() {
	// System.out.println("  hogehoge");
	// }
	//
	// @After
	// public void execAfter() {
	// System.out.println("@.After.");
	// }
	//
	// @AfterClass
	// public static void execAfterClass() {
	// System.out.println("*** AfterClass.");
	// }
}