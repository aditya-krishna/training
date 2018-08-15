package com.example.demo;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Component;

@Component
public class VerificationDao implements BaseDao<Verification, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationDao.class);

    private static final String COMMA = ",";
    private static final String PLACEHOLDER = "=?";
    private static final String TABLE = "\"PIN_VERIFICATION\"";
    private static final String WHERE = " WHERE ";

    private static final String INSERT = new StringBuffer("INSERT INTO public.").append(TABLE).append(" (")
            .append(VerificationTableColumns.DEVICE_ID.getText()).append(COMMA)
            .append(VerificationTableColumns.VERIFIED.getText()).append(" )")
            .append(" VALUES ")
            .append("(?, ?)")
            .toString();

    private static final String QUERY = new StringBuffer("SELECT * FROM public.").append(TABLE)
            .append(WHERE)
            .append(VerificationTableColumns.DEVICE_ID.getText())
            .append(PLACEHOLDER)
            .toString();

    private static final String UPDATE = new StringBuffer("UPDATE public.").append(TABLE)
            .append(" SET ")
            .append(VerificationTableColumns.ATTEMPTS.getText()).append("=").append(VerificationTableColumns.ATTEMPTS.getText())
            .append("+1").append(COMMA)
            .append(VerificationTableColumns.VERIFIED.getText()).append(PLACEHOLDER).append(COMMA)
            .append(VerificationTableColumns.CHANGED_DATE_TIME.getText()).append(PLACEHOLDER)
            .append(WHERE)
            .append(VerificationTableColumns.DEVICE_ID.getText()).append(PLACEHOLDER)
            .toString();

    private JdbcTemplate jdbcTemplate;

    private PreparedStatementCreatorFactory insertPstmtFactory;
    private PreparedStatementCreatorFactory queryPstmtFactory;
    private PreparedStatementCreatorFactory updatePstmtFactory;

    public VerificationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initPreparedStatements() {
        List<SqlParameter> querySqlParameterList = new ArrayList<>();
        querySqlParameterList.add(new SqlParameter(Types.VARCHAR));

        queryPstmtFactory = new PreparedStatementCreatorFactory(QUERY, querySqlParameterList);

        List<SqlParameter> insertSqlParameterList = new ArrayList<>();
        insertSqlParameterList.add(new SqlParameter(Types.VARCHAR));
        insertSqlParameterList.add(new SqlParameter(Types.CHAR));

        insertPstmtFactory = new PreparedStatementCreatorFactory(INSERT, insertSqlParameterList);

        List<SqlParameter> updateSqlParameterList = new ArrayList<>();
        updateSqlParameterList.add(new SqlParameter(Types.CHAR));
        updateSqlParameterList.add(new SqlParameter(Types.TIMESTAMP));
        updateSqlParameterList.add(new SqlParameter(Types.VARCHAR));

        updatePstmtFactory = new PreparedStatementCreatorFactory(UPDATE, updateSqlParameterList);
    }

    @Override
    public Verification findById(String id) {
        List<Object> paramsList = new ArrayList<>();
        paramsList.add(id);
        return jdbcTemplate.query(queryPstmtFactory.newPreparedStatementCreator(paramsList), new VerificationMapper());
    }

    @Override
    public void save(Verification data) {
        long timestamp = Instant.now().toEpochMilli();

        Verification pinVerification = findById(data.getDeviceId());

        if (pinVerification != null) {
            pinVerification.setChangedDateTime(new Timestamp(timestamp));
            pinVerification.setVerified(data.getVerified());

            update(pinVerification);
        }
        else {
            create(data);
        }
    }

    /**
     * Create a new entry in the table
     * 
     * @param data
     *            Data to be inserted in the table
     */
    private void create(Verification data) {

        List<Object> paramsList = new ArrayList<>();

        paramsList.add(data.getDeviceId());
        paramsList.add(data.getVerified());

        int updateCount = jdbcTemplate.update(insertPstmtFactory.newPreparedStatementCreator(paramsList));

    }

    /**
     * Update an existing entry in the table
     * 
     * @param data
     *            Data to be updated
     */
    private void update(Verification data) {

        List<Object> paramsList = new ArrayList<>();

        paramsList.add(data.getVerified());
        paramsList.add(data.getChangedDateTime());
        paramsList.add(data.getDeviceId());

        int updateCount = jdbcTemplate.update(updatePstmtFactory.newPreparedStatementCreator(paramsList));

    }
}
