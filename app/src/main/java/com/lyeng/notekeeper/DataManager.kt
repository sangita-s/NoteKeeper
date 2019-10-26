package com.lyeng.notekeeper

//class DataManager {  -- to make it a singleton
object DataManager {
    val hashmapOfTeaType = hashMapOf<String, TeaTypeInfo>()
    val listOfTeas = ArrayList<TeaInfo>()

    init {
        initializeCourses()
        initializeNotes()
    }

    fun addNote(tea: TeaTypeInfo, teaName: String, teaMsg: String): Int {
        val tea = TeaInfo(tea, teaName, teaMsg)
        listOfTeas.add(tea)
        return listOfTeas.lastIndex
    }

    fun findNote(teaType: TeaTypeInfo, teaName: String, teaMsg: String): TeaInfo? {
        for (tea in listOfTeas) { // == equals methos invoked. java - object
            if (teaType == tea.textTeaType
                && teaName == tea.textTeaName
                && teaMsg == tea.textMessage
            )
                return tea
        }
        return null
    }

    private fun initializeCourses() {
        var course = TeaTypeInfo("01_black_tea", "The Black Tea")
        hashmapOfTeaType.set(course.tesTypeId, course)

        course = TeaTypeInfo(tesTypeId = "02_milk_tea", teaTypeName = "The Milk Tea")
        hashmapOfTeaType.set(course.tesTypeId, course)

        course = TeaTypeInfo(tesTypeId = "03_herb_tea", teaTypeName = "The Herb Tea")
        hashmapOfTeaType.set(course.tesTypeId, course)

        course = TeaTypeInfo(tesTypeId = "04_green_tea", teaTypeName = "The Green Tea")
        hashmapOfTeaType.set(course.tesTypeId, course)
    }

    fun initializeNotes() {

        var course = hashmapOfTeaType["01_black_tea"]!!
        var note = TeaInfo(
            course, "The Blackest Black Tea",
            "It is good!"
        )
        listOfTeas.add(note)
        note = TeaInfo(
            course, "The Blackest Black Tea 2",
            "PendingIntents are powerful"
        )
        listOfTeas.add(note)

        course = hashmapOfTeaType["02_milk_tea"]!!
        note = TeaInfo(
            course, "The Milkiest Milk Tea",
            "Did you know that by default an "
        )
        listOfTeas.add(note)
        note = TeaInfo(
            course, "The Milkiest Milk Tea 2",
            "Foreground Services can be tied to a "
        )
        listOfTeas.add(note)

        course = hashmapOfTeaType["03_herb_tea"]!!
        note = TeaInfo(
            course, "The Herbiest Tea",
            "Leverage variable-length parameter lists"
        )
        listOfTeas.add(note)
        note = TeaInfo(
            course, "The Herbiest Tea 2",
            "Anonymous classes simplify implementing one-use types"
        )
        listOfTeas.add(note)

        course = hashmapOfTeaType["04_green_tea"]!!
        note = TeaInfo(
            course, "The greenest green tea",
            "The -jar option isn't compatible"
        )
        listOfTeas.add(note)
        note = TeaInfo(
            course, "The greenest green tea 2",
            "Remember to include SerialVersionUID"
        )
        listOfTeas.add(note)
    }

    fun loadNotes(vararg noteIds: Int): List<TeaInfo> {
        simulateLoadDelay()
        val noteList: List<TeaInfo>
        if(noteIds.isEmpty())
            noteList = listOfTeas
        else{
            noteList = ArrayList<TeaInfo>(noteIds.size)
            for(noteId in noteIds)
                noteList.add(listOfTeas[noteId])
        }
        return noteList
    }

    fun loadNote(noteId: Int) = listOfTeas[noteId]

    fun isLastNoteId(noteId: Int) = noteId == listOfTeas.lastIndex

    fun idOfNote(note: TeaInfo) = listOfTeas.indexOf(note)

    fun noteIdsAsIntArray(notes: List<TeaInfo>): IntArray {
        val noteIds = IntArray(notes.size)
        for(index in 0..notes.lastIndex)
            noteIds[index] = DataManager.idOfNote(notes[index])
        return noteIds
    }

    private fun simulateLoadDelay() {
        Thread.sleep(1000)
    }
}
