package io.pivotal.pal.tracker

class InMemoryTimeEntryRepository : TimeEntryRepository {

    private var currentId = 1L
    private var timeEntryList : MutableList<TimeEntry> = mutableListOf()

    override fun create(timeEntry: TimeEntry): TimeEntry {
        val timeEntryResult = TimeEntry(currentId++, timeEntry.projectId!!, timeEntry.userId!!, timeEntry.date!!, timeEntry.hours!!)
        timeEntryList.add(timeEntryResult)
        return timeEntryResult
    }

    override fun find(id: Long?): TimeEntry? {
        return timeEntryList.find { it.id == id }
    }

    override fun list(): List<TimeEntry> {
        return timeEntryList
    }

    override fun update(id: Long?, timeEntry: TimeEntry): TimeEntry? {
        val toUpdate = this.find(id)
        if (toUpdate != null) {
            toUpdate.projectId = timeEntry.projectId
            toUpdate.userId = timeEntry.userId
            toUpdate.date = timeEntry.date
            toUpdate.hours = timeEntry.hours
            return toUpdate
        }
        return null
    }

    override fun delete(id: Long?) {
        timeEntryList.removeIf { it.id == id }
    }
}
