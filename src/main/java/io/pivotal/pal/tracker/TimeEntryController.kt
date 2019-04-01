package io.pivotal.pal.tracker

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.Mapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("time-entries")
class TimeEntryController(private val timeEntryRepository: TimeEntryRepository) {

    @PostMapping
    fun create(@RequestBody timeEntry: TimeEntry): ResponseEntity<TimeEntry> {
        val created = timeEntryRepository.create(timeEntry)
        return ResponseEntity(created, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun read(@PathVariable id: Long): ResponseEntity<TimeEntry> {
        val found = timeEntryRepository.find(id)
        return if (found == null) ResponseEntity(HttpStatus.NOT_FOUND)
            else ResponseEntity(found, HttpStatus.OK)
    }

    @GetMapping
    fun list(): ResponseEntity<List<TimeEntry>> {
        val list = timeEntryRepository.list()
        return ResponseEntity(list, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody timeEntry: TimeEntry): ResponseEntity<TimeEntry> {
        val updated = timeEntryRepository.update(id, timeEntry)
        return if (updated == null) ResponseEntity(HttpStatus.NOT_FOUND)
             else ResponseEntity(updated, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<TimeEntry> {
        timeEntryRepository.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
