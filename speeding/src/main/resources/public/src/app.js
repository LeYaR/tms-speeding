    $.doAjax = function(url, obj) {
        return new Promise(function(resolve, reject) {
            $.ajax(url, {
                data: JSON.stringify(obj),
                contentType: 'application/json',
                type: 'POST',
                success: function(data) {
                    resolve(data);
                },
                error: function(data) {
                    reject(data);
                }
            });
        });
    }

    function isEmpty(el) {
        return el == null || !el.toString().trim().length;
    }

    function emptyVal(val) {
        return val == null ? '' : val;
    }

    function getSize() {
        let avl = window.screen.availHeight;
        let inn = window.innerHeight;
        let out = window.outerHeight;
        return avl <= out ? inn: avl - out + inn + 21;
    }

    function toastus(message, type) {
        $('body').toast({
        class: type ? type : 'success',
        displayTime: 4000,
        message: message
        });
    }

    function fillObject(target, object) {
        Object.keys(target).forEach(function(el) {
            if (typeof object[el] !== "undefined") {
                target[el] = object[el];
            }
        });
    }

    function clearObject(object) {
        Object.keys(object).forEach(function(el) {
            object[el] = null;
        });
    }

    Vue.component('popup', {
        props: ['position', 'hover'],
        mounted: function() {
        let self = this;
        $(this.$el).popup({
            inline     : true,
            hoverable  : self.hover,
            position   : self.position,
            on: 'hover',
            delay: {
            show: 300,
            hide: 300
            }
        });
        }
    });

    Vue.component('checkbox-frm', {
        props: ['data'],
        methods: {
        init: function() {
            if (this.data.value) {
            $(this.$el).checkbox('set checked');
            } else if (this.data.value == null) {
                $(this.$el).checkbox('set indeterminate');
            } else {
            $(this.$el).checkbox('set unchecked');
            }
        }
        },
        mounted: function() {
        let self = this;
        $(self.$el).checkbox({
            onChange: function() {
            self.$emit('updated', $(self.$el).checkbox('is checked'));
            }
        });
        this.init();
        },
        watch: {
        'data.value': function() {
            this.init();
        }
        },
        template: '<div class="ui checkbox"><input type="checkbox" class="hidden"><label>{{data.label}}</label></div>'
    });

    Vue.component('dropdown-frm', {
        props: ['data'],
        mounted: function() {
            let self = this;
            $(self.$el).dropdown({
                values: self.data.list
            }).dropdown('set selected', self.data.list.length ? self.data.value : null).dropdown({
                onChange: function(val) {
                    self.$emit('updated', val);
                }
            });
        },
        template: '<div class="ui selection dropdown"><input type="hidden" /><div class="text"></div><i class="dropdown icon"></i><div class="menu"></div></div>'
    });

    Vue.component('calendar-date', {
        props: ['date'],
        mounted: function() {
        let self = this;
        $(self.$el).calendar("set date", self.date).calendar({type: 'date',
            monthFirst: false,
            formatter: {
                date: function (date, settings) {
                if (!date) return '';
                var day = ('0' + (date.getDate())).slice(-2);
                var month = ('0' + (date.getMonth() + 1)).slice(-2);
                var year = date.getFullYear();
                return day + '-' + month + '-' + year;
                },
                time: function (date, settings) {
                if (!date) return '';
                var hrs = date.getHours();
                var min = ('0' + (date.getMinutes())).slice(-2);
                return hrs + ':' + min;
                }
            },
            firstDayOfWeek: 0,
            onChange: function(date, mode) {
                self.$emit('updated', mode);
            }/*,
            text: {
                days: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
                months: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
                monthsShort: ['Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек'],
                today: 'Сегодня',
                now: 'Сейчас',
                am: 'AM',
                pm: 'PM'
            }*/
        });
        },
        /*watch: {
            date: function(newVal, oldVal) {
                $(this.$el).calendar("set date", newVal, false, false);
            }
        },*/
        template: '<div class="ui calendar"><div class="ui input left icon"><i class="calendar icon"></i><input type="text" placeholder="..."></div></div>'
    });

    
    var EntityList = class {
        constructor (name, url, limit) {
            this.name = name;
            this.limit = limit;
            this.url = url;
            this.form = {};
            this.list = {
                page: 1,
                count: 0,
                data: []
            };
            this.isLoaded = false;
            this.search = null;
        }
    }

    var main = new Vue({
        el: '#main',
        data: {
            isReady: false,
            activeTab: 'people',
            isCreated: false,
            grids: {
                people: new EntityList("people", "people", Math.floor((getSize() - 245) / 41)),
                inspectors: new EntityList("inspectors", "inspectors", Math.floor((getSize() - 245) / 41)),
                violations: new EntityList("violations", "violations", Math.floor((getSize() - 245) / 41))
            },
            forms: {
                people: {
                    visible: false,
                    data: {
                        id: null,
                        firstName: null,
                        lastName: "as",
                        middleName: "vs",
                        bornDate: null,
                        personalNumber: null
                    }
                }
            },
            directories: {
                countries: [],
                regions: [],
                marks: [],
                models: [],
                ranks: [],
                departments: []
            },

        },
        watch: {
            activeTab: function(newVal, oldVal) {
                if (!this.grids[newVal].isLoaded) {
                    this.doLongRoutine(this.processList(newVal)).catch(function() {
                        toastus('Unable to get ' + newVal + ' list', 'error');
                    });
                }
            }
        },
        created: function() {
            let self = this;
            this.isCreated = true;
            $.post('dirs', {}).then(function(data) {
                fillObject(self.directories, data);
                self.isReady = true;
                return self.processList(self.activeTab);
            }).catch(
                function() {
                    toastus('Unable to get directories list', 'error');
                }
            );
        },
        computed: {
            isPersonFormValid: function() {
                return !isEmpty(this.forms.people.data.lastName) && 
                       !isEmpty(this.forms.people.data.firstName) && 
                       !isEmpty(this.forms.people.data.bornDate);
            },
            gridStats: function() {
                let result = {};
                let self = this;
                Object.keys(this.grids).forEach(function(el) {
                    result[el] = {
                        pages: Math.ceil(self.grids[el].list.count / self.grids[el].limit),
                        empty: Math.max(0, self.grids[el].limit - self.grids[el].list.data.length),
                        startWith: (self.grids[el].list.page - 1) * self.grids[el].limit + 1
                    }
                });
                return result;
            }
        },
        methods: {
            saveForm: function(form) {
                let self = this;
                let list = this.activeTab;
                let page = this.grids[list].list.page;
                $.doAjax(form + '/save', this.forms[form].data).then(function(data) {
                    toastus(data.message, data.type);
                    return self.processList(list, page)
                }).then(function() {
                    self.forms[form].visible = false;
                }).catch(function(data) {
                    toastus('Unable to save the data', 'error');
                });
            },
            openForm: function(form, val) {
                fillObject(this.forms[form].data, val);
                this.forms[form].visible = true;
            },
            clearForm: function(val) {
                clearObject(this.forms[val].data);
                this.forms[val].visible = true;
            },
            getDir: function(dir, val) {
                let row = this.directories[dir].filter(function(el) {
                    return el.id == val;
                });

                return row.length ? row[0].title : null;
            },
            doSearch: function(val) {
                if (val != this.grids[this.activeTab].search) {
                    this.grids[this.activeTab].search = val;
                    this.getList();
                }
            },
            getList: function(page) {
                let list = this.activeTab;
                if (!page) {
                    page = 1;
                }
                this.doLongRoutine(this.processList(list, page)).catch(function(data) {
                    toastus("Unable to retrieve data", "red");
                });
            },
            processList: function(list, page) {
                let self = this;
                let url = this.grids[list].url;
                let search = this.grids[list].search;
                page = page ? page : 1;
                let options = {page: page, limit: this.grids[list].limit};
                if (!isEmpty(search)) {
                    options.search = search;
                }
                return new Promise(function(resolve, reject) {
                    $.post(url, options).then(function(data) {
                        self.grids[list].isLoaded = true;
                        self.grids[list].list.count = data.data.count;
                        self.grids[list].list.data = data.data.list;
                        self.grids[list].list.page = page;
                        resolve();
                    }).catch(function(data) {
                        self.grids[list].isLoaded = false;
                        reject(data);
                    });
                });
            },
            doLongRoutine: function(promise) {
                let self = this;
                let interval = setTimeout(function() {
                    self.isReady = false;
                }, 2000);
                return new Promise(function(resolve, reject) {
                    promise.then(function(data) {
                        self.isReady = true;
                        resolve(data);
                    }).catch(function(data) {
                        reject(data);
                    }).finally(function() {
                        clearTimeout(interval);
                    });
                });
            }
        }
    });