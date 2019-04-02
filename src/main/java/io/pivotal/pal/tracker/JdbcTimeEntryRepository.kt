package io.pivotal.pal.tracker

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCallback
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.time.LocalDate
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Time
import javax.sql.DataSource

class JdbcTimeEntryRepository (datasource: DataSource): TimeEntryRepository {
    private val jdbcTemplate = JdbcTemplate(datasource)
    private var timeEntryList : MutableList<TimeEntry> = mutableListOf()

    override fun create(timeEntry: TimeEntry): TimeEntry {
        val kH = GeneratedKeyHolder()
        val sql = "INSERT INTO time_entries (project_id, user_id, date, hours) VALUES (?, ?, ?, ?)"
        jdbcTemplate.update(
            { connection ->
                val ps = connection.prepareStatement(sql, arrayOf("id"))
                ps.setLong(1, timeEntry.projectId?: 0)
                ps.setLong(2, timeEntry.userId?: 0)
                ps.setString(3, timeEntry.date?.toString()?: "")
                ps.setInt(4, timeEntry.hours?: 0)
                ps
            }, kH)
        timeEntry.id = kH.key?.toLong()
        return timeEntry
    }

    override fun find(id: Long?): TimeEntry? {
        val sql = "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id=${id}"
        val resultList = jdbcTemplate.query(sql, TimeEntryMapper())
        return if (resultList.size == 0) null else return resultList[0]
    }

    override fun list(): List<TimeEntry> {
        val sql = "SELECT id, project_id, user_id, date, hours FROM time_entries"
        return jdbcTemplate.query(sql, TimeEntryMapper())
    }

    override fun update(id: Long?, timeEntry: TimeEntry): TimeEntry? {
        val toUpdate = this.find(id)
        if (toUpdate != null) {
                val sql = "UPDATE time_entries SET project_id = ?, user_id = ?, date = ?, hours = ? WHERE id=?"
                jdbcTemplate.update { connection ->
                    val ps = connection.prepareStatement(sql, arrayOf("id"))
                    ps.setLong(1, timeEntry.projectId?: 0)
                    ps.setLong(2, timeEntry.userId?: 0)
                    ps.setString(3, timeEntry.date?.toString()?: "")
                    ps.setInt(4, timeEntry.hours?: 0)
                    ps.setLong(5, id?: -1)
                    ps
                }
            timeEntry.id = id
            return timeEntry
        }
        return null
    }

    override fun delete(id: Long?) {
        jdbcTemplate.execute("DELETE FROM time_entries WHERE id=$id")
    }
}

private class TimeEntryMapper : RowMapper<TimeEntry> {
    override fun mapRow(rs: ResultSet, rowNum: Int): TimeEntry? {
        val timeEntry = TimeEntry()

        timeEntry.id = rs.getLong("id")
        timeEntry.projectId = rs.getLong("project_id")
        timeEntry.userId = rs.getLong("user_id")
        timeEntry.date = LocalDate.parse(rs.getString("date"))
        timeEntry.hours = rs.getInt("hours")

        return timeEntry
    }
}