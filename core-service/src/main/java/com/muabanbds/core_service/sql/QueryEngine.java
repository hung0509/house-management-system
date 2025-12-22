package com.muabanbds.core_service.sql;


import com.muabanbds.common_service.dto.BaseQueryRequest;
import com.muabanbds.common_service.sql.Pagination;
import com.muabanbds.common_service.sql.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Service;


import javax.sql.DataSource;
import java.sql.*;

@Slf4j
public class QueryEngine {

    private final DataSource dataSource;

    public QueryEngine(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ResultSet getRecords( String tableName, Parameter parameter,
                                  BaseQueryRequest base)  {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            CallableStatement callableStatement = connection.prepareCall("{? = call pos.d_query_engine(?, ?::json, ?, ?, ?)}");
            // Đăng ký tham số OUT cho con trỏ
            callableStatement.registerOutParameter(1, Types.REF_CURSOR); // Cụ thể cho PostgreSQL

            // Thiết lập các tham số đầu vào
            callableStatement.setString(2, tableName);
            callableStatement.setString(3, parameter.listToJson());
            callableStatement.setInt(4, base.getPageSize());
            callableStatement.setInt(5, base.getPage());
            callableStatement.setString(6, base.getSortBy() + " " + base.getOrder());

            log.info("Callable: " + callableStatement.toString());

            // Thực hiện câu lệnh
            callableStatement.execute();

            return (ResultSet) callableStatement.getObject(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;

    }


    public ResultSet getRecordsWithoutPaging( String tableName, Parameter parameter)  {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            CallableStatement callableStatement = connection.prepareCall("{? = call pos.d_query_engine(?, ?::json, ?, ?, ?)}");
            // Đăng ký tham số OUT cho con trỏ
            callableStatement.registerOutParameter(1, Types.REF_CURSOR); // Cụ thể cho PostgreSQL

            // Thiết lập các tham số đầu vào
            callableStatement.setString(2, tableName);
            callableStatement.setString(3, parameter.listToJson());
            callableStatement.setInt(4, -1);
            callableStatement.setInt(5, -1);
            callableStatement.setString(6, "");

            log.info("Callable: " + callableStatement.toString());

            // Thực hiện câu lệnh
            callableStatement.execute();

            return (ResultSet) callableStatement.getObject(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    public Pagination getPagination(String tableName,
                                    Parameter parameter, BaseQueryRequest baseReq){
        Pagination pagination = null;
        try(Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement  preparedStatement =  connection
                    .prepareCall("SELECT * FROM pos.d_pagination_engine(?, ?::json, ?, ?)");
            preparedStatement.setString(1, tableName);
            preparedStatement.setString(2, parameter.listToJson());
            preparedStatement.setInt(3, baseReq.getPageSize());
            preparedStatement.setInt(4, baseReq.getPage());

            log.info("PrepateStatement: " + preparedStatement.toString());

            ResultSet rs  =  preparedStatement.executeQuery();

            if(rs.next()){
                pagination  = Pagination.builder()
                        .pageSize(rs.getInt("page_size"))
                        .page(rs.getInt("current_page"))
                        .totalPage(rs.getInt("total_pages"))
                        .totalCount(rs.getLong("total_records"))
                        .build();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return pagination;
    }
}
