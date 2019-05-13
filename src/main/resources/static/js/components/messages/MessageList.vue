<template>
    <v-layout align-space-around justify-start column>
        <message-form :messages="messages" :messageAttr="message" :profile="profile" />
        <message-row v-for="message in sortedMessages"
                     :key="message.id"
                     :message="message"
                     :editMessage="editMessage"
                     :deleteMessage="deleteMessage"
                     :messages="messages" />
    </v-layout>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'

    export default {
        props: ['messages', 'profile'], //list of message положенных в frontendDate
        components: {
            MessageRow,
            MessageForm
        },
        data() {
            return {
                message: null //"текущий" message
            }
        },
        computed: {
            sortedMessages() {
                return this.messages.sort((a, b) => (new Date(a.deadlineDate).getTime() - new Date(b.deadlineDate).getTime()))
            }
        },
        methods: {
            editMessage(message) {
                this.message = message
            },
            deleteMessage(message) {
                this.$resource('/message{/id}').remove({id: message.id}).then(result => {
                    if (result.ok) {
                        this.messages.splice(this.messages.indexOf(message), 1)
                    }
                })
            }
        }
    }
</script>

<style>

</style>