<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout row>
        <v-layout align-space-around justify-start column>
            <v-layout align-space-around justify-start column>

                <v-text-field
                        label="New task"
                        placeholder="Write something"
                        v-model="title"
                ></v-text-field>

                <v-text-field
                        label="Description"
                        placeholder="Write something"
                        v-model="text"
                ></v-text-field>

            </v-layout>
            <v-layout align-start justify-start column>

                <v-menu
                        ref="menu"
                        v-model="menu"
                        :close-on-content-click="false"
                        :nudge-right="40"
                        :return-value.sync="deadlineDate"
                        lazy
                        transition="scale-transition"
                        offset-y
                        full-width
                        min-width="290px"
                >
                    <template v-slot:activator="{ on }">
                        <v-text-field
                                v-model="deadlineDate"
                                label="Picker in menu"
                                prepend-icon="event"
                                readonly
                                v-on="on"
                        ></v-text-field>
                    </template>
                    <v-date-picker v-model="deadlineDate"
                                   first-day-of-week="1"
                                   no-title scrollable>
                        <v-spacer></v-spacer>
                        <v-btn flat color="primary" @click="menu = false">Cancel</v-btn>
                        <v-btn flat color="primary" @click="$refs.menu.save(deadlineDate)">OK</v-btn>
                    </v-date-picker>
                </v-menu>

            </v-layout>
        </v-layout>
        <v-btn @click="save">
            Save
        </v-btn>
    </v-layout>
</template>

<script>
    function getIndex(list, id) {
        for (var i = 0; i < list.length; i++ ) {
            if (list[i].id === id) {
                return i
            }
        }

        return -1
    }

    export default {
        props: ['messages', 'messageAttr', 'profile'],
        data() {
            return {
                title: '',
                text: '',
                id: '',
                deadlineDate: '',
                menu: ''
            }
        },
        watch: {
            messageAttr(newVal, oldVal) {
                this.deadlineDate = newVal.deadlineDate;
                this.title = newVal.title;
                this.text = newVal.text;
                this.id = newVal.id
            }
        },
        methods: {
            save() {

                const message = {
                    userId: this.profile.id,
                    text: this.text,
                    title: this.title,
                    deadlineDate:
                        // new Date(
                            this.deadlineDate
                        // )
                    ,
                };

                if (this.id) {
                    this.$resource('/message{/id}').update({id: this.id}, message).then(result =>
                        result.json().then(data => {
                            const index = getIndex(this.messages, data.id);
                            this.messages.splice(index, 1, data);
                            this.text = '';
                            this.id = ''
                        })
                    )
                } else {
                    this.$resource('/message{/id}').save({}, message).then(result =>
                        result.json().then(data => {
                            this.messages.push(data);
                            this.text = ''
                        })
                    )
                }
            }
        }
    }
</script>

<style>

</style>