package exposedandroid.exposedandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

        transaction {

            SchemaUtils.create(Cities)

            // insert new city. SQL: INSERT INTO Cities (name) VALUES ('St. Petersburg')
            val stPeteId = Cities.insert {
                it[name] = "St. Petersburg"
            } get Cities.id

            // 'select *' SQL: SELECT Cities.id, Cities.name FROM Cities
            println("Cities: ${Cities.selectAll()}")
        }
    }
}

object Cities : IntIdTable() {
    val name = varchar("name", 50)
}
