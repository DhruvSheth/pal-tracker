package io.pivotal.pal.tracker

import java.time.LocalDate

class TimeEntry() {
    var id: Long? = null
    var projectId: Long? = null
    var userId: Long? = null
    var date: LocalDate? = null
    var hours: Int? = null

    constructor(projectId: Long, userId: Long, date: LocalDate, hours: Int):
        this() {
        this.projectId = projectId
        this.userId = userId
        this.date = date
        this.hours = hours
    }

    constructor(id: Long, projectId: Long, userId: Long, date: LocalDate, hours: Int):
        this(projectId, userId, date, hours) {
        this.id = id
    }

    override fun equals(other: Any?): Boolean {
        if (other is TimeEntry) {
            return this.id == other.id
                && this.projectId == other.projectId
                && this.userId == other.userId
                && this.hours == other.hours
        }
        return false
    }

    override fun hashCode(): Int {
        return this.id.hashCode() + this.projectId.hashCode() + this.userId.hashCode() + this.hours.hashCode()
    }
}
