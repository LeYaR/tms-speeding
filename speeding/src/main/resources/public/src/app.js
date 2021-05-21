    Date.prototype.addDays = function(val) {
        var date = new Date(this.valueOf());
        date.setDate(date.getDate() + val);
        return date;
    }
    
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

    function fillObject(target, object, path) {
        Object.keys(target).forEach(function(el) {
            if (typeof object[el] !== "undefined") {
                if (object[el] != null && typeof object[el] == "object" && !Array.isArray(object[el])) {
                    if (path) {
                        fillObject(target[el], object[el], path + '.' + el);
                    } else {
                        fillObject(target[el], object[el]);
                    }
                } else {
                    if (path) {
                        setByPath(target[el], path, Array.isArray(target[el]) &&  object[el] == null ? [] : object[el]);
                        //path.split('.').reduce(getIndex, target[el]) = object[el];
                    } else {
                        target[el] = object[el];
                    }
                }
            }
        });
    }

    function setByPath(obj, path, val) {
        let arr = path.split('.');
        for (let i = 0; i < arr.length - 1; i++) {
            obj = obj[i];
        }
        obj[arr[arr.length - 1]] = val;
    }

    function clearObject(object) {
        Object.keys(object).forEach(function(el) {
            if (object[el] != null && typeof object[el] == 'object') {
                clearObject(object[el]);
            } else {
                object[el] = null;
            }
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

    Vue.component('manual-popup', {
        props: ['position', 'visible'],
        mounted: function() {
            let self = this;
            $(this.$el).popup({
                inline     : true,
                position   : self.position,
                on: 'manual',
                delay: {
                show: 300,
                hide: 300
                },
                closable: false,
                exclusive: true,
                onHide: function() {
                    if (self.visible) {
                        self.$emit('hidden', false);
                    }
                }
            });
            if (self.visible) {
                $(this.$el).popup('show');
            }
        },
        watch: {
            visible: function(newVal, oldVal) {
                if (newVal) {
                    $(this.$el).popup('show');
                } else {
                    $(this.$el).popup('hide');
                }
            }
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
            if (isEmpty(this.data.value) && !$(self.$el).hasClass('clearable')) {
                this.data.value = self.data.list.length ? self.data.list[0].value : null;
            }
            $(self.$el).dropdown({
                values: self.data.list
            }).dropdown({
                onChange: function(val) {
                    self.$emit('updated', val ? val : null);
                }
            }).dropdown('set selected', self.data.value);
        },
        watch: {
            'data.value': function(newVal, oldVal) {
                if (newVal == null && $(this.$el).hasClass('clearable')) {
                    $(this.$el).dropdown('clear');
                }
            }
        },
        template: '<div class="ui selection dropdown"><input type="hidden" /><div class="text"></div><i class="dropdown icon"></i><div class="menu"></div></div>'
    });

    Vue.component('calendar-date', {
        props: ['date', 'minimum'],
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
            minDate: self.minimum ? self.minimum : null,
            onChange: function(date, mode) {
                self.$emit('updated', mode.length ? mode : null);
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
        watch: {
            date: function(newVal, oldVal) {
                if (!newVal) {
                    $(this.$el).calendar('clear');
                }
            }
        },
        template: '<div class="ui calendar"><div class="ui input left icon"><i class="calendar icon"></i><input type="text" placeholder="..."></div></div>'
    });
    
    var EntityList = class {
        constructor (name, url, limit) {
            this.name = name;
            this.limit = limit;
            this.url = url;
            this.list = {
                page: 1,
                count: 0,
                data: []
            };
            this.isLoaded = false;
            this.search = null;
        }
    }

    var DirectoryForm = class {
        constructor (label, form, limit) {
            this.label = label;
            this.form = form;
            this.limit = limit;
            this.page = 1;
            this.list = [];
        }
    }

    var gridLimit = Math.floor((getSize() - 245) / 43);
    var dirsLimit = Math.floor((getSize() - 239) / 41.6);;

    var main = new Vue({
        el: '#main',
        data: {
            isReady: false,
            activeTab: 'people',
            activeDir: 'countries',
            isCreated: false,
            isDirEdited: false,
            grids: {
                people: new EntityList("people", "people", gridLimit),
                inspectors: new EntityList("inspectors", "inspectors", gridLimit),
                vehicles: new EntityList("vehicles", "vehicles", gridLimit),
                violations: new EntityList("violations", "violations", gridLimit)
            },
            forms: {
                people: {
                    visible: false,
                    data: {
                        id: null,
                        firstName: null,
                        lastName: null,
                        middleName: null,
                        bornDate: null,
                        personalNumber: null
                    }
                },
                inspectors: {
                    visible: false,
                    data: {
                        id: null,
                        person: {id: null,
                                 lastName: null,
                                 firstName: null,
                                 middleName: null,
                                 bornDate: null,
                                 personalNumber: null},
                        badgeNumber: null,
                        rank: null,
                        department: null
                    }
                },
                vehicles: {
                    visible: false,
                    data: { id: null,
                            model: null,
                            regNumber: null,
                            region: null,
                            vin: null
                    }
                },
                violations: {
                    filterEnabled: false,
                    isFiltered: false,
                    data: {
                        date: null,
                        guilty: {id: null, text: null},
                        inspector: {id: null, text: null},
                        vehicle: {id: null, text: null},
                        region: null
                    }
                },
                generation: {
                    isActive: false,
                    data: {
                        start: null,
                        end: null,
                        limit: null
                    }
                }
            },
            redirection: {
                active: false,
                source: null,
                target: null,
                context: null
            },
            directories: {
                countries: new DirectoryForm('Countries', {id: null, title: null, iso: null}, dirsLimit),
                regions: new DirectoryForm('Regions', {id: null, title: null, country: null}, dirsLimit),
                marks: new DirectoryForm('Marks', {id: null, title: null}, dirsLimit),
                models: new DirectoryForm('Models', {id: null, title: null, mark: null}, dirsLimit),
                ranks: new DirectoryForm('Ranks', {id: null, title: null}, dirsLimit),
                departments: new DirectoryForm('Departments', {id: null, title: null}, dirsLimit)
            },

        },
        watch: {
            activeTab: function(newVal, oldVal) {
                if (newVal == 'inspectors' && this.isRedirectionActive('inspectors')) {
                    this.redirection.active = false;
                }
                if (newVal != 'directories' && !this.grids[newVal].isLoaded) {
                    this.doLongRoutine(this.processList(newVal)).catch(function() {
                        toastus('Unable to get ' + newVal + ' list', 'error');
                    });
                }
            }
        },
        created: function() {
            let self = this;
            this.isCreated = true;
            $.post('dirs', {limit: dirsLimit}).then(function(data) {
                fillObject(self.directories, data, 'list')
                //fillObject(self.directories, data, 'data');
                return self.processList(self.activeTab);
            }).then(function() {self.isReady = true;}).catch(function() {
                    toastus('Unable to get directories list', 'error');
                }
            );
        },
        computed: {
            isGenrationSet: function() {
                return !isEmpty(this.forms.generation.data.start) ||
                       !isEmpty(this.forms.generation.data.end) ||
                       !isEmpty(this.forms.generation.data.limit);
            },
            isGenerationOk: function() {
                let start = this.stringToDate(this.forms.generation.data.start);
                let end = this.stringToDate(this.forms.generation.data.end);
                let limit = this.forms.generation.data.limit;
                return !isEmpty(start) && !isEmpty(end) && start.addDays(31) <= end &&
                       parseInt(limit) && parseInt(limit) > 0 && parseInt(limit) <= 10000
            },
            isReadyForFiltering: function() {
                return !isEmpty(this.forms.violations.data.date) ||
                       !isEmpty(this.forms.violations.data.guilty.id) ||
                       !isEmpty(this.forms.violations.data.inspector.id) ||
                       !isEmpty(this.forms.violations.data.vehicle.id) ||
                       !isEmpty(this.forms.violations.data.region)
            },
            dropdownLists: function() {
                let self = this;
                return {
                    countries: self.directories.countries.list.map(function(el) {
                        return {name: el.title, value: el.id};
                    }),
                    marks: self.directories.marks.list.map(function(el) {
                        return {name: el.title, value: el.id};
                    }),
                    ranks: self.directories.ranks.list.map(function(el) {
                        return {name: el.title, value: el.id};
                    }),
                    departments: self.directories.departments.list.map(function(el) {
                        return {name: el.title, value: el.id};
                    }),
                    models: self.directories.models.list.map(function(el) {
                        return {name: el.title, value: el.id};
                    }),
                    regions: self.directories.regions.list.map(function(el) {
                        return {name: el.title, value: el.id};
                    })
                }
            },
            isActiveDirValid: function() {
                return !isEmpty(this.directories[this.activeDir].form.title);
            },
            isVehicleFormValid: function() {
                return !isEmpty(this.forms.vehicles.data.vin) && 
                       !isEmpty(this.forms.vehicles.data.regNumber) && 
                       !isEmpty(this.forms.vehicles.data.model);
            },
            isPersonFormValid: function() {
                return !isEmpty(this.forms.people.data.lastName) && 
                       !isEmpty(this.forms.people.data.firstName) && 
                       !isEmpty(this.forms.people.data.bornDate);
            },
            isInspectorFormValid: function() {
                return !isEmpty(this.forms.inspectors.data.person.lastName) && 
                       !isEmpty(this.forms.inspectors.data.person.firstName) && 
                       !isEmpty(this.forms.inspectors.data.person.bornDate) &&
                       !isEmpty(this.forms.inspectors.data.badgeNumber);
            },
            dirsStats: function() {
                let result = {};
                let self = this;
                Object.keys(this.directories).forEach(function(el) {
                    result[el] = {
                        start: (self.directories[el].page - 1) * self.directories[el].limit,
                        end: self.directories[el].page * self.directories[el].limit - 1,
                        pages: Math.ceil(self.directories[el].list.length / self.directories[el].limit),
                        empty: Math.max(self.directories[el].page * self.directories[el].limit - self.directories[el].list.length, 0)
                    }
                });
                return result;
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
            stringToDate: function(val) {
                if (isEmpty(val)) {
                    return null;
                }
                let arr = val.split('-');
                return new Date(arr[2], parseInt(arr[1]) - 1, parseInt(arr[0]));
            },
            generateCustomRows: function() {
                this.generateRows(this.forms.generation.data);
            },
            generateRows: function(val) {
                let self = this;
                let options = val ? val : {}
                let chain = Promise.resolve().then(function() {
                    return $.post('violations/generate/', options);
                }).then(function() {
                    return self.processList('violations');
                });
                this.doLongRoutine(chain).catch(function(data) {
                    console.log(data);
                    toastus('Something went wrong', 'error');
                });
            },
            prepareFilter: function() {
                let filter = {};
                let obj = this.forms.violations.data;
                Object.keys(obj).forEach(function(el) {
                    if (obj[el] != null && typeof obj[el].id != 'undefined') {
                        filter[el] = obj[el].id
                    } else {
                        filter[el] = obj[el];
                    }
                });

                return filter;
            },
            filterData: function() {
                let obj = this.forms.violations.data;
                let self = this;
                let filter = this.prepareFilter();
                this.processList('violations', 1, filter).then(function() {
                    self.forms.violations.isFiltered = true;
                })
            },
            resolveRedirect: function(val) {
                switch (this.redirection.source) {
                    case 'inspectors':
                        this.forms.inspectors.data.person = val;
                        this.clearRedirection();
                        break;
                    case 'violations':
                        this.forms.violations.data[this.redirection.context].id = val.id;
                        if (this.redirection.target == 'people') {
                            this.forms.violations.data[this.redirection.context].text = val.lastName + ' ' + val.firstName + ' ' + val.middleName;
                        } else {
                            this.forms.violations.data.vehicle.text = val.vin + ' / ' + val.regNumber;
                        }
                        this.clearRedirection();
                        break;
                }
            },
            setRedirection: function(target, context) {
                this.redirection.source = this.activeTab;
                this.redirection.target = target;
                this.redirection.active = true;
                this.activeTab = target;
                if (context) {
                    this.redirection.context = context;
                }
            },
            clearRedirection: function() {
                this.redirection.active = false;
                this.redirection.context = null;
                this.activeTab = this.redirection.source;
            },
            isRedirectionActive: function(val) {
                return this.redirection.active && ~val.indexOf(this.redirection.source);
            },
            clearFilter: function() {
                clearObject(this.forms.violations.data);
                this.forms.violations.isFiltered = false;
                this.getList();
            },
            setFilter: function() {
                if (!this.forms.violations.filterEnabled) {
                    this.forms.violations.filterEnabled = true;
                } else {
                    this.forms.violations.filterEnabled = false;
                }
            },
            clearDir: function() {
                clearObject(this.directories[this.activeDir].form);
                this.isDirEdited = true;
            },
            openDir: function(val) {
                fillObject(this.directories[this.activeDir].form, val);
                this.isDirEdited = true;
            },
            getDir: function(page) {
                return false;
            },
            saveDir: function() {
                let self = this;
                let dir = self.activeDir;
                $.doAjax('dirs/' + dir + '/save', this.directories[dir].form).then(function() {
                    return $.post('dirs/' + dir + '/', {});
                }).then(function(data) {
                    self.directories[dir].list = data;
                    self.isDirEdited = false;
                }).catch(function() {
                    toastus('Something went wrong', 'error');
                });
            },
            saveForm: function(form) {
                let self = this;
                let chain = $.doAjax(form + '/save', this.forms[form].data).then(function(data) {
                    toastus(data.message, data.type);
                    return Promise.resolve();
                });
                Object.keys(this.grids).forEach(function(el) {
                    if (self.grids[el].isLoaded) {
                        chain = chain.then(function () {
                            return self.processList(el, self.grids[el].list.page);
                        });
                    }
                });
                self.doLongRoutine(chain).then(function() {
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
            getDirEntry: function(dir, val) {
                let row = this.directories[dir].list.filter(function(el) {
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
                let filter = null;
                if (list == 'violations' && this.forms.violations.isFiltered) {
                    filter = this.prepareFilter(this.forms.violations.data);
                }
                this.doLongRoutine(this.processList(list, page, filter)).catch(function(data) {
                    toastus("Unable to retrieve data", "red");
                });
            },
            processList: function(list, page, filter) {
                let self = this;
                let url = this.grids[list].url;
                let search = this.grids[list].search;
                page = page ? page : 1;
                let options = {};
                if (filter) {
                    options = filter;
                    options.page = page;
                    options.limit = this.grids[list].limit;
                } else {
                    options = {page: page, limit: this.grids[list].limit};
                    if (!isEmpty(search)) {
                        options.search = search;
                    }
                }
                let chain = Promise.resolve();
                return new Promise(function(resolve, reject) {
                    if (filter) {
                        chain = chain.then(function() {
                            return $.doAjax(url + '/filter/', options);
                        });
                    } else {
                        chain = chain.then(function() {
                            return $.post(url, options);
                        });
                    }
                    chain.then(function(data) {
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