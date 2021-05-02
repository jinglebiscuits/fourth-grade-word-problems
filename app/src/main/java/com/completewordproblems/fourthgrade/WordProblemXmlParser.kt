package com.completewordproblems.fourthgrade

import android.util.Xml
import com.completewordproblems.fourthgrade.models.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

private val ns: String? = null

public class WordProblemXmlParser {

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<WordProblem> {
        inputStream.use {
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(it, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): List<WordProblem> {
        val wordProblems = mutableListOf<WordProblem>()

        parser.require(XmlPullParser.START_TAG, ns, "word_problems")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // Starts by looking for the word_problem tag
            if (parser.name == "problem") {
                wordProblems.add(readWordProblem(parser))
            } else {
                skip(parser)
            }
        }
        return wordProblems
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun readWordProblem(parser: XmlPullParser): WordProblem {
        val wordProblem = WordProblem()
        var answer = ""
        var concepts = arrayListOf<Concept>()
        var wordProblemSegments = arrayListOf<WordProblemSegment>()
        parser.require(XmlPullParser.START_TAG, ns, "problem")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "word_problem_segments" -> wordProblemSegments = readWordProblemSegments(parser)
                "concepts" -> concepts = readConcepts(parser)
                "answer" -> answer = readAnswer(parser)
                else -> skip(parser)
            }
        }
        wordProblem.segments = wordProblemSegments
        wordProblem.answer = answer
        wordProblem.concepts = concepts
        return wordProblem
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readWordProblemSegments(parser: XmlPullParser): ArrayList<WordProblemSegment> {
        var wordProblemSegments = arrayListOf<WordProblemSegment>()
        parser.require(XmlPullParser.START_TAG, ns, "word_problem_segments")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "segment" -> wordProblemSegments.add(readWordProblemSegment(parser))
                else -> skip(parser)
            }
        }
        return wordProblemSegments
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readWordProblemSegment(parser: XmlPullParser): WordProblemSegment {
        parser.require(XmlPullParser.START_TAG, ns, "segment")
        var segment = ""
        var isNecessary = false
        var isMainObjective = false
        var keyWords = arrayListOf<KeyWord>()
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "text" -> segment = readSegmentText(parser)
                "is_necessary" -> isNecessary = readIsNecessary(parser)
                "is_main_objective" -> isMainObjective = readIsMainObjective(parser)
                "key_words" -> keyWords = readKeyWords(parser)
                else -> skip(parser)
            }
        }
        val wpSegment = WordProblemSegment(segment, isNecessary, isMainObjective)
        keyWords.forEach { wpSegment.addKeyword(it) }
        return wpSegment
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readConcepts(parser: XmlPullParser): ArrayList<Concept> {
        var concepts = arrayListOf<Concept>()
        parser.require(XmlPullParser.START_TAG, ns, "concepts")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "concept" -> concepts.add(readConcept(parser))
                else -> skip(parser)
            }
        }
        return concepts
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readConcept(parser: XmlPullParser): Concept {
        parser.require(XmlPullParser.START_TAG, ns, "concept")
        var standards = arrayListOf<Standard>()
        var mastery = 0
        var health = 0
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "standards" -> standards = readStandards(parser)
                "mastery" -> mastery = readInt(parser, "mastery")
                "health" -> health = readInt(parser, "health")
                else -> skip(parser)
            }
        }
        return Concept(standards, mastery, health)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readStandards(parser: XmlPullParser): ArrayList<Standard> {
        val standards = arrayListOf<Standard>()
        parser.require(XmlPullParser.START_TAG, ns, "standards")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "standard" -> standards.add(readStandard(parser))
                else -> skip(parser)
            }
        }
        return standards
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readStandard(parser: XmlPullParser): Standard {
        parser.require(XmlPullParser.START_TAG, ns, "standard")
        var standardType = StandardType.COMMON_CORE
        var id = ""
        var description = ""
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "type" -> standardType = readStandardType(parser)
                "id" -> id = readId(parser)
                "description" -> description = readDescription(parser)
                else -> skip(parser)
            }
        }
        return Standard(standardType, id, description)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readStandardType(parser: XmlPullParser): StandardType {
        parser.require(XmlPullParser.START_TAG, ns, "type")
        val standardType = when (readText(parser)) {
            "COMMON_CORE" -> StandardType.COMMON_CORE
            "ALABAMA" -> StandardType.ALABAMA
            "NAEP" -> StandardType.NAEP
            else -> StandardType.COMMON_CORE
        }
        parser.require(XmlPullParser.END_TAG, ns, "type")
        return standardType
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readId(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "id")
        val id = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "id")
        return id
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readDescription(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "description")
        val description = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "description")
        return description
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readKeyWords(parser: XmlPullParser): ArrayList<KeyWord> {
        val keyWords = arrayListOf<KeyWord>()
        parser.require(XmlPullParser.START_TAG, ns, "key_words")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "key_word" -> keyWords.add(readKeyWord(parser))
                else -> skip(parser)
            }
        }
        return keyWords
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readKeyWord(parser: XmlPullParser): KeyWord {
        parser.require(XmlPullParser.START_TAG, ns, "key_word")
        var start = 0
        var end = 0
        var word = ""
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "start" -> start = readKeyWordStart(parser)
                "end" -> end = readKeyWordEnd(parser)
                "word" -> word = readKeyWordWord(parser)
                else -> skip(parser)
            }
        }
        return KeyWord(start, end, word, Concept(listOf(), 0, 0))
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readKeyWordStart(parser: XmlPullParser): Int {
        parser.require(XmlPullParser.START_TAG, ns, "start")
        val start = readInt(parser)
        parser.require(XmlPullParser.END_TAG, ns, "start")
        return start
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readKeyWordEnd(parser: XmlPullParser): Int {
        parser.require(XmlPullParser.START_TAG, ns, "end")
        val end = readInt(parser)
        parser.require(XmlPullParser.END_TAG, ns, "end")
        return end
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readKeyWordWord(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "word")
        val word = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "word")
        return word
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readSegmentText(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "text")
        val text = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "text")
        return text
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readIsNecessary(parser: XmlPullParser): Boolean {
        parser.require(XmlPullParser.START_TAG, ns, "is_necessary")
        val isNecessary = readBoolean(parser)
        parser.require(XmlPullParser.END_TAG, ns, "is_necessary")
        return isNecessary
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readIsMainObjective(parser: XmlPullParser): Boolean {
        parser.require(XmlPullParser.START_TAG, ns, "is_main_objective")
        val isMainObjective = readBoolean(parser)
        parser.require(XmlPullParser.END_TAG, ns, "is_main_objective")
        return isMainObjective
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readAnswer(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "answer")
        val answer = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "answer")
        return answer
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readBoolean(parser: XmlPullParser): Boolean {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result == "true"
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readInt(parser: XmlPullParser, tag: String): Int {
        parser.require(XmlPullParser.START_TAG, ns, tag)
        var result = 0
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text.toInt()
            parser.nextTag()
        }
        parser.require(XmlPullParser.END_TAG, ns, tag)
        return result
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readInt(parser: XmlPullParser): Int {
        var result = 0
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text.toInt()
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }

}