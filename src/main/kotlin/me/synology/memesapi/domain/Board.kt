package me.synology.memesapi.domain

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
class Board(
    title: String,
    content: String,
    information: BoardInformation,
    writer: User,
    tags: Set<Tag>
) : PrimaryKeyEntity() {

    @Column(nullable = false)
    var createAt: LocalDateTime = LocalDateTime.now()
        protected set

    @Column(nullable = false)
    var title: String = title
        protected set

    @Column(nullable = false, length = 3000)
    var content: String = content
        protected set

    @Embedded
    var information: BoardInformation = information
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var writer: User = writer
        protected set

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "board_tag_assoc",
        joinColumns = [JoinColumn(name = "board_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")],
    )
    protected val mutableTags: MutableSet<Tag> = mutableSetOf()
    val tags: Set<Tag> get() = mutableTags.toSet()

    @ElementCollection
    @CollectionTable(name = "board_comment")
    private val mutableComments: MutableList<Comment> = mutableListOf()
    val comments: List<Comment> get() = mutableComments.toList()

    fun update(data: BoardUpdateData) {
        title = data.title
        content = data.content
        information = data.information
    }

    fun addTag(tag: Tag) {
        mutableTags.add(tag)
    }

    fun removeTag(tagId: UUID) {
        mutableTags.removeIf { it.id == tagId }
    }

    fun addComment(comment: Comment) {
        mutableComments.add(comment)
    }

    init {
        writer.writeBoard(this)
    }
}

@Embeddable
data class BoardInformation(
    @Column(length = 3000)
    val link: String?,

    @Column(nullable = false)
    var rank: Int,
)

@Embeddable
data class Comment(
    @Column(length = 3000)
    val content: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    val writer: User,
)

data class BoardUpdateData(
    val title: String,
    val content: String,
    val information: BoardInformation,
)
