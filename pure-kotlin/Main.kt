data class Student(
    val name: String,
    val grades: List<Double>,
    val average: Double
)

fun main() {
    val students = collectStudentData()
    showMenu(students)
}

fun collectStudentData(): List<Student> {
    println("Welcome to the Student Menu!")
    val studentCount = promptForStudentCount()

    if (studentCount == 0) return emptyList()

    val students = mutableListOf<Student>()

    repeat(studentCount) { index ->
        println("\n--- Student ${index + 1} ---")

        print("Enter student name: ")
        val name = readLine() ?: "Unknown"

        val grades = mutableListOf<Double>()
        print("How many grades for this student? ")
        val gradeCount = readLine()?.toIntOrNull() ?: 0

        repeat(gradeCount) { gradeIndex ->
            print("Enter grade ${gradeIndex + 1}: ")
            val grade = readLine()?.toDoubleOrNull() ?: 0.0
            grades.add(grade)
        }

        val average = if (grades.isNotEmpty()) grades.average() else 0.0
        students.add(Student(name, grades, average))
    }

    return students
}

fun promptForStudentCount(): Int {
    while (true) {
        print("How many students are being evaluated? ")
        val input = readLine()?.toIntOrNull()

        when {
            input == null -> println("Please enter a valid number.")
            input < 0 -> println("Please enter a positive number.")
            input == 0 -> {
                println("No students to evaluate.")
                return 0
            }
            else -> return input
        }
    }
}

fun showMenu(students: List<Student>) {
    while (true) {
        println("\n=== Main Menu ===")
        println("1. View Stats")
        println("2. Quit")
        print("Choose an option: ")

        when (readLine()) {
            "1" -> viewStats(students)
            "2" -> {
                println("Goodbye!")
                return
            }
            else -> println("Invalid option. Please choose 1 or 2.")
        }
    }
}

fun viewStats(students: List<Student>) {
    if (students.isEmpty()) {
        println("\nNo students to display.")
        return
    }

    val sortedStudents = students.sortedByDescending { it.average }

    println("\n=== Student Rankings (by GPA) ===")
    sortedStudents.forEachIndexed { index, student ->
        println("${index + 1}. ${student.name}")
        println("   Grades: ${student.grades.joinToString(", ")}")
        println("   Average: ${"%.2f".format(student.average)}")
    }
}
